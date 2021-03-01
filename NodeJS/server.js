var express = require('express');
var app = express();
var bodyParser = require("body-parser");
var mysql = require('mysql');
var async = require('async');
const { json } = require('body-parser');
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({
    extended: true
}));

function generateAccessToken() {
    return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function (c) {
        var r = Math.random() * 16 | 0, v = c == 'x' ? r : (r & 0x3 | 0x8);
        return v.toString(16);
    });
}
generateAccessTokenSCB();

var minutes = 5, the_interval = minutes * 60 * 1000;
setInterval(function () {
    console.log("I am doing my 5 minutes check");
    generateAccessTokenSCB();
}, the_interval);

function generateAccessTokenSCB() {
    var request = require('request');

    var headers = {
        'Content-Type': 'application/json',
        'accept-language': 'EN',
        'requestUId': 'xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx',
        'resourceOwnerId': 'xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx'
    };

    var dataString = '{ "applicationKey" : "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx", "applicationSecret" : "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx" }';

    var options = {
        url: 'https://api-sandbox.partners.scb/partners/sandbox/v1/oauth/token',
        method: 'POST',
        headers: headers,
        body: dataString
    };

    function callback(error, response, body) {
        if (!error && response.statusCode == 200) {
            console.log(body);
            var accessToken = JSON.parse(body)['data']['accessToken'];
            global.accessTokenGB = accessToken;
        }
    }

    request(options, callback);

};

function genDeeplink(res, price, orderId) {
    var request = require('request');

    var headers = {
        'content-type': 'application/json',
        'authorization': 'Bearer ' + global.accessTokenGB,
        'accept-language': 'EN',
        'resourceOwnerId': 'xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx',
        'requestUId': 'xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx',
        'channel': 'scbeasy'
    };

    var dataString = '{ "transactionType": "PURCHASE", "transactionSubType": ["BP"], "sessionValidityPeriod": 60, "sesisionValidUntil": "", "billPayment": { "paymentAmount": ' + price + ', "accountTo": "265347483628042", "ref1": "' + price + '", "ref2": "' + orderId + '", "ref3": "MOS123" } }';

    var options = {
        url: 'https://api-sandbox.partners.scb/partners/sandbox/v3/deeplink/transactions',
        method: 'POST',
        headers: headers,
        body: dataString
    };

    function callback(error, response, body) {
        if (!error && response.statusCode == 201) {
            console.log(body);
            var deepLink = JSON.parse(body)['data']['deeplinkUrl'];

            return res.send(deepLink);
        }
    }

    request(options, callback);


}
// default route 
app.post('/', function (req, res) {
    genDeeplink(res, req.body.price, req.body.order_id)
    // return res.send({ error: true, message: 'Home Dec API' })
});
// connection configurations 
var dbconn = mysql.createConnection({
    host: 'localhost',
    user: 'xxxxxxxxxxxxx',
    password: 'xxxxxxxxxxxxx',
    database: 'xxxxxxxxxxx'
});
// connect to database 
dbconn.connect();

// Retrieve all students 
app.get('/alluser', function (req, res) {
    dbconn.query('SELECT * FROM user', function (error, results, fields) {
        if (error) throw error;
        res.setHeader('Content-Type', 'application/json');
        return res.send(results);
    });
});

app.post('/gettransaction', async function (req, res) {
    var transactiondata = req.body;
    console.log(transactiondata);
    dbconn.query("INSERT INTO payment SET ? ", transactiondata, function (error, results, fields) {
        if (error) throw error;
        dbconn.query("UPDATE orders SET Orders_status = 'paid' ,payment_id = ? WHERE Order_id = ?", [results.insertId, transactiondata.billPaymentRef2], function (error, results, fields) {
            if (error) throw error;
           
        });
       
    });
    
  
    return res.send({ status: "success" });
  });
// Add a new Student 
app.post('/adduserold', function (req, res) {
    var user = req.body
    if (!user) {
        return res.status(400).send({ error: true, message: 'Please provide user ' });
    }
    dbconn.query("INSERT INTO user SET ? ", user, function (error, results, fields) {
        if (error) throw error;
        return res.send(results);
    });
});

app.post('/userlogin', function (req, res) {
    console.log(req.body)
    var username = req.body.username
    var password = req.body.password
    var accesstoken = ""
    dbconn.query("SELECT username, user_email, user_type FROM user WHERE username = ? AND password = ?;", [username, password], function (error, results, fields) {
        if (error) throw error;
        res.setHeader('Content-Type', 'application/json');
        var tokenid;
        if (results.length > 0) {
            console.log("login pass")
            tokenid = generateAccessToken();
            dbconn.query("INSERT INTO accesstoken SET ? ", { token_id: tokenid, username: username }, function (error, results, fields) {
                if (error) throw error;

            });
            return res.send({ error: false, status: "success", access_token: tokenid, data: results[0] });
        } else {
            console.log("login fail")
            return res.send({ error: true, status: "fail", data: {} });
        }

    });

});

function validateAccessToken(token_id) {

    dbconn.query("SELECT username FROM accesstoken WHERE token_id = '" + token_id + "' ;", function (error, results, fields) {
        if (error)
            throw error;
        var tokenid;
        if (results.length > 0) {
            console.log(results[0]["username"]);
            return results[0]["username"];
        } else {

            return null;
        }

    });
};

app.post('/getuserdata', function (req, res) {

    var tokenid = req.body.tokenid
    var username = null
    var dataquery;

    dbconn.query("SELECT username FROM accesstoken WHERE token_id = '" + tokenid + "' ;", function (error, results, fields) {
        if (error) return res.send({ error: true, status: "fail", data: {} });;

        if (results.length > 0) {
            username = results[0]["username"]
            dbconn.query('SELECT * FROM user WHERE username = ?', username, function (error, resultss, fields) {
                if (error) return res.send({ error: true, status: "fail", data: {} });;
                dataquery = resultss[0]
                return res.send({ error: false, status: "success", data: dataquery });


            });
        } else {
            return res.send({ error: true, status: "fail", data: {} });
        }

    });

});

app.get('/allproduct', function (req, res) {
    var searchtext = req.query.searchtext
    var querytext = 'SELECT * FROM product '
    if (searchtext != undefined) {
        querytext += "WHERE product_name LIKE '%" + searchtext + "%'"
    }
    dbconn.query(querytext, function (error, results, fields) {
        if (error) throw error;
        res.setHeader('Content-Type', 'application/json');
        return res.send({ error: false, status: "success", data: results });
        // return res.send(results);
    });
});

app.post('/allorder', function (req, res) {
    var user_id = undefined
    if (req.body.tokenid != undefined) {
        dbconn.query("SELECT user_id FROM accesstoken INNER JOIN user on accesstoken.username = user.username WHERE accesstoken.token_id = '" + req.body.tokenid + "' ;", function (error, results, fields) {
            if (results.length > 0) {
                console.log(results[0]["user_id"])
                user_id = results[0]["user_id"]
                var andUser = ""
                if (user_id != undefined) {

                    andUser += " AND user_id = '" + user_id + "'"
                }
                console.log(user_id + "+++++++++++++++++++++++++++++++++++++++")
                var searchtext = req.query.order_status

                var querytext = 'SELECT * FROM orders '
                if (searchtext != undefined) {
                    querytext += "WHERE Orders_status = '" + searchtext + "' " + andUser
                }
                console.log(querytext)
                dbconn.query(querytext, function (error, results, fields) {
                    if (error) throw error;
                    res.setHeader('Content-Type', 'application/json');
                    return res.send({ error: false, status: "success", data: results });
                    // return res.send(results);
                });
            }
        });
    } else {
        var searchtext = req.query.order_status
        var querytext = 'SELECT * FROM orders '
        if (searchtext != undefined) {
            querytext += "WHERE Orders_status = '" + searchtext + "' "
        }
        console.log(querytext)
        dbconn.query(querytext, function (error, results, fields) {
            if (error) throw error;
            res.setHeader('Content-Type', 'application/json');
            return res.send({ error: false, status: "success", data: results });
            // return res.send(results);
        });
    }


});

app.post('/changepassword', function (req, res) {
    var tokenid = req.body.tokenid
    var username = null
    var newPassword = req.body.newpassword
    res.setHeader('Content-Type', 'application/json');
    dbconn.query("SELECT username FROM accesstoken WHERE token_id = '" + tokenid + "' ;", function (error, results, fields) {
        if (error) return res.send({ error: true, status: "fail", data: {} });;

        if (results.length > 0) {
            username = results[0]["username"]
            dbconn.query('UPDATE user set password = ? WHERE username = ?', [newPassword, username], function (error, resultss, fields) {
                if (error) return res.send({ error: true, status: "fail", data: {} });

                return res.send({ error: false, status: "success", data: {} });


            });
        } else {
            return res.send({ error: true, status: "fail", data: {} });
        }

    });
});

app.post('/edituser', function (req, res) {
    var tokenid = req.body.tokenid
    var userdata = req.body
    delete userdata.tokenid
    res.setHeader('Content-Type', 'application/json');
    dbconn.query("SELECT username FROM accesstoken WHERE token_id = '" + tokenid + "' ;", function (error, results, fields) {
        if (error) return res.send({ error: true, status: "fail", data: {} });;

        if (results.length > 0) {
            username = results[0]["username"]
            dbconn.query('UPDATE user set ? WHERE username = ?', [userdata, username], function (error, resultss, fields) {
                if (error) return res.send({ error: true, status: "fail", data: {} });
                console.log(username)
                console.log(userdata)
                return res.send({ error: false, status: "success", data: {} });


            });
        } else {
            return res.send({ error: true, status: "fail", data: {} });
        }

    });
});

app.post('/adduser', function (req, res) {
    var userdata = req.body
    if (!userdata) {
        return res.send({ error: true, status: "fail", data: {} });
    }

    dbconn.query("SELECT username FROM user WHERE username = ? ", userdata["username"], function (error, results, fields) {
        if (error) return res.send({ error: true, status: "fail", data: {} });;

        if (results.length > 0) {
            return res.send({ error: true, status: "fail", data: {} });;

        } else {
            dbconn.query("INSERT INTO user SET ? ", userdata, function (error, results, fields) {
                if (error) return res.send({ error: true, status: "fail", data: {} });
                return res.send({ error: false, status: "success", data: {} });
            });
        }

    });
});

function minusProduct(productId, Amount) {
    dbconn.query('UPDATE product set balancestock = balancestock - ? WHERE product_id = ?', [Amount, productId], function (error, resultss, fields) {
        if (error) return;

        return


    });
}


app.post('/updatetracking', function (req, res) {
    dbconn.query('UPDATE orders set trackingnumber = ? WHERE Order_id = ?', [req.body.trackingnumber, req.body.order_id], function (error, resultss, fields) {
        if (error) return res.send({ error: true, status: "fail", data: {} });

        return res.send({ error: false, status: "success", data: {} });


    });

});

app.post('/getorderdetail', function (req, res) {
    dbconn.query("SELECT * FROM orderdetail LEFT JOIN product ON orderdetail.product_id = product.product_id WHERE orderdetail.order_id = ?", req.body.order_id, function (error, results, fields) {
        if (error) return res.send({ error: true, status: "fail", data: {} });;

        return res.send({ error: false, status: "success", data: results });

    });
});

app.post('/addorderdetail', function (req, res) {
    var order_id = req.body.order_id
    var productamount = req.body.productamount
    var price = req.body.price
    var totalprice = req.body.totalprice
    var product_id = req.body.product_id

    minusProduct(product_id, productamount)

    var data = {
        order_id: order_id,
        productamount: productamount,
        price: price,
        totalprice: totalprice,
        product_id: product_id
    }

    dbconn.query("INSERT INTO orderdetail SET ? ", data, function (error, results, fields) {
        if (error) return res.send({ error: true, status: "fail", data: {} });;
        console.log()
        return res.send({ error: false, status: "success", data: {} });

    });
});


app.post('/getusertype', function (req, res) {
    var tokenid = req.body.tokenid
    var userdata = req.body
    console.log(tokenid)
    res.setHeader('Content-Type', 'application/json');
    dbconn.query("SELECT username FROM accesstoken WHERE token_id = '" + tokenid + "' ;", function (error, results, fields) {
        if (error) return res.send({ error: true, status: "fail", data: {} });;

        if (results.length > 0) {

            username = results[0]["username"]

            dbconn.query('SELECT user_type FROM user WHERE username = ?', username, function (error, resultss, fields) {
                if (error) return res.send({ error: true, status: "fail", data: {} });
                console.log(username)

                return res.send({ error: false, status: "success", data: resultss[0] });


            });
        } else {
            return res.send({ error: true, status: "fail", data: {} });
        }

    });
});

function addproducthastype(productid, typeid) {
    console.log(productid, typeid)
    dbconn.query("INSERT INTO producthastype SET ? ", { product_id: productid, productType_id: typeid }, function (error, results, fields) {
        if (error) throw error;

    });
}

app.post('/addproduct', function (req, res) {
    var tokenid = req.body.tokenid
    var userdata = req.body
    delete userdata.tokenid
    type = JSON.parse(userdata["productType_id"])
    delete userdata.productType_id

    res.setHeader('Content-Type', 'application/json');
    dbconn.query("SELECT username FROM accesstoken WHERE token_id = '" + tokenid + "' ;", function (error, results, fields) {
        if (error) return res.send({ error: true, status: "fail", data: {} });;

        if (results.length > 0) {

            username = results[0]["username"]

            dbconn.query('SELECT user_type FROM user WHERE username = ?', username, function (error, resultss, fields) {
                if (error) return res.send({ error: true, status: "fail", data: {} });
                console.log(username)

                if (resultss[0]["user_type"] == "admin") {
                    console.log("thisisadmin")
                    dbconn.query("INSERT INTO product SET ? ", userdata, function (error, results, fields) {
                        if (error) throw error;
                        console.log(results)
                        type.forEach(element => addproducthastype(results.insertId, element));
                        return res.send(results);
                    });

                }



            });
        } else {
            return res.send({ error: true, status: "fail", data: {} });
        }

    });
});

app.post('/addorder', function (req, res) {
    let date_ob = new Date();

    // current date
    // adjust 0 before single digit date
    let date = ("0" + date_ob.getDate()).slice(-2);

    // current month
    let month = ("0" + (date_ob.getMonth() + 1)).slice(-2);

    // current year
    let year = date_ob.getFullYear();

    // current hours
    let hours = date_ob.getHours();

    // current minutes
    let minutes = date_ob.getMinutes();

    // current seconds
    let seconds = date_ob.getSeconds();
    var tokenid = req.body.tokenid
    var userdata = req.body
    delete userdata.tokenid
    console.log(userdata)
    res.setHeader('Content-Type', 'application/json');
    dbconn.query("SELECT user_id FROM accesstoken INNER JOIN user on accesstoken.username = user.username WHERE accesstoken.token_id = '" + tokenid + "' ;", function (error, results, fields) {
        if (error) return res.send({ error: true, status: "fail", data: {} });;

        if (results.length > 0) {
            console.log(results[0]["user_id"])
            user_id = results[0]["user_id"]
            userdata["user_id"] = user_id
            userdata["Orders_status"] = "unpaid"
            userdata["Orders_date"] = year + "-" + month + "-" + date + " " + hours + ":" + minutes + ":" + seconds

            dbconn.query("INSERT INTO orders SET ? ", userdata, function (error, results, fields) {
                if (error) throw error;
                console.log(results)
                return res.send(results.insertId.toString());
            });






        } else {
            return res.send({ error: true, status: "fail", data: {} });
        }

    });
});






app.post('/test', function (req, res) {

    console.log(req.body)
    return res.send({ error: true, status: "fail", data: {} });



});


//set port
app.listen(13000, function () {
    console.log('Node app is running on port 13000');
})

app.post('/getproducttype', function (req, res) {
    var productid = req.body["product_id"]
    var producttype_id = req.body["producttype_id"]
    console.log(req.body["productid"])
    dbconn.query('SELECT product.product_id as product_id, producthastype.productType_id as producttype_id ,producttype.productType_name as producttype_name, product.product_name, product.product_price, product.balancestock FROM product INNER JOIN producthastype ON product.product_id = producthastype.product_id INNER JOIN producttype on producthastype.productType_id = producttype.productType_id', function (error, results, fields) {
        if (error) throw error;
        res.setHeader('Content-Type', 'application/json');
        console.log(results)
        if (productid != undefined) {
            var i;
            for (i = 0; i < results.length; i++) {
                if (results[i]["product_id"] != productid) {
                    delete results[i]
                }
            }
        }
        var results = results.filter(function (el) {
            return el != null;
        });
        if (producttype_id != undefined) {
            var i;
            for (i = 0; i < results.length; i++) {
                if (results[i]["producttype_id"] != producttype_id) {
                    delete results[i]
                }
            }
        }
        var results = results.filter(function (el) {
            return el != null;
        });
        return res.send({ error: false, status: "success", data: results });
        // return res.send(results);
    });
});

app.post('/removeproduct', function (req, res) {
    var productid = req.body["product_id"]
    res.setHeader('Content-Type', 'application/json');

    dbconn.query('DELETE FROM product WHERE product_id = ?;', productid, function (error, results, fields) {
        if (error) return res.send({ error: true, status: "fail", data: {} });
        dbconn.query('DELETE FROM producthastype WHERE product_id = ?;', productid, function (error, resultss, fields) {
            if (error) return res.send({ error: true, status: "fail", data: {} });

            return res.send({ error: false, status: "success", data: {} });
            //return res.send({ error: false, status: "success", data: {} });
            // return res.send(results);
        });
    });

    // return res.send(results);
});


module.exports = app;
