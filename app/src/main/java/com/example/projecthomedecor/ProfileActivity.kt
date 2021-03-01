package com.example.projecthomedecor

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.*
import kotlinx.android.synthetic.main.fragment_add_items_admin.*
import kotlinx.android.synthetic.main.fragment_change_password.*
import kotlinx.android.synthetic.main.fragment_edit_profile.*
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_register.*
import kotlinx.android.synthetic.main.fragment_user_menu.*

class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        supportActionBar?.hide()

    }
    override fun onResume() {
        super.onResume()
        if (Global.loginStatus == null) {
            supportFragmentManager.beginTransaction().add(
                R.id.profileframe,
                LoginFragment()
            ).commit()
        } else {

            supportFragmentManager.beginTransaction().add(
                R.id.profileframe,
                UserMenuFragment()
            ).commit()
        }
    }

    fun gohome(item: MenuItem) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        finish()
    }

    fun gocategory(item: MenuItem) {
        val intent = Intent(this, Category::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        finish()
    }

    fun gocart(item: MenuItem) {
        val intent = Intent(this, CartActivity::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        finish()
    }

    fun goprofile(item: MenuItem) {

    }

    fun register(view: View) {
        supportFragmentManager.beginTransaction().add(
            R.id.profileframe,
            RegisterFragment()
        ).addToBackStack(null).commit()
    }

    fun login(view: View) {
        if (DatabaseApi.loginCheck(
                edt_username_login.text.toString(),
                edt_password_login.text.toString()
            ) == "success"
        ) {
//            Toast.makeText(applicationContext, Global.accessToken, Toast.LENGTH_LONG).show()
            finish()
            startActivity(getIntent())
        } else {
            Toast.makeText(applicationContext, "ชื่อหรือรหัสผ่านไม่ถูกต้อง", Toast.LENGTH_LONG).show()
        }

    }

    fun logoutbutton(item: MenuItem) {
        val myBuilder = AlertDialog.Builder(this)
        myBuilder.apply {
            setTitle("ออกจากระบบ")
            setMessage("คุณต้องการออกจากระบบหรือไม่?")
            setNegativeButton("ยืนยัน") { dialog, which ->
                Global.loginStatus = null
                finish()
                startActivity(getIntent())
            }
            setPositiveButton("ยกเเลิก") { dialog, which -> dialog.cancel() }
            show()
        }
    }

    fun checkshipping(item: MenuItem) {
        supportFragmentManager.beginTransaction().add(
            R.id.profileframe,
            CheckStatusUserFragment()
        ).addToBackStack(null).commit()
    }

    fun delivery(item: MenuItem) {

    }

    fun story(item: MenuItem) {
        supportFragmentManager.beginTransaction().add(
            R.id.profileframe,
            HistoryOrderFragment()
        ).addToBackStack(null).commit()
    }

    fun editprofile(item: MenuItem) {
        supportFragmentManager.beginTransaction().add(
            R.id.profileframe,
            EditProfileFragment()
        ).addToBackStack(null).commit()
    }

    fun changepass(item: MenuItem) {
        supportFragmentManager.beginTransaction().add(
            R.id.profileframe,
            ChangePasswordFragment()
        ).addToBackStack(null).commit()
    }

    fun changpass(view: View) {
        if (passwordnew.getText().toString().equals(acceptpassword.getText().toString())) {
            if (DatabaseApi.getCurrentPassword().equals(passCurrent.getText().toString())) {
                if (DatabaseApi.changePassword(passwordnew.getText().toString()) == "success") {
                    supportFragmentManager.popBackStack()
                    return Toast.makeText(applicationContext, "Successfully", Toast.LENGTH_SHORT).show();
                }
            }
        }
        return Toast.makeText(applicationContext, "Incorrect", Toast.LENGTH_SHORT)
            .show();
    }

    fun cancelEdit(view: View) {
        supportFragmentManager.beginTransaction().add(
            R.id.profileframe,
            UserMenuFragment()
        ).commit()
    }
    fun saveEdit(view: View) {
        val selectID :Int = radio_group.checkedRadioButtonId
        var gender = ""
        if(radio_female.isChecked){
            gender = "female"
        }else gender = "male"
        if(DatabaseApi.editUser( edt_name.text.toString(),edt_email.text.toString(), gender.toString() , edt_address.text.toString() , edt_phone.text.toString(), show_HBD.text.toString()) == "success"){
            supportFragmentManager.popBackStack()
        }
    }
    fun showDatePickerDialog(view: View) {
        val newDateFragment = DatePickerFragment()
        newDateFragment.show(supportFragmentManager, "Date Picker")
    }

    fun showDatePickerDialogRegis(view: View) {
        val newDateFragment = DatePickerFragmentRegister()
        newDateFragment.show(supportFragmentManager, "Date Picker")
    }
    fun cancelRegis(view: View) {
        supportFragmentManager.beginTransaction().add(
            R.id.profileframe,
            LoginFragment()
        ).commit()
    }
    fun btn_register(view: View) {
        var gender = ""
        if(edt_regis_password.getText().toString().equals(edt_regis_acceptpassword.getText().toString())){
            if(radio_regis_female.isChecked){
                gender = "female"
            }else gender = "male"

            if(DatabaseApi.addUser(edt_regis_username.text.toString(), edt_regis_password.text.toString() ,edt_regis_name.text.toString(), edt_regis_email.text.toString(), gender.toString(), edt_regis_address.text.toString(),edt_regis_phone.text.toString(),show_regis_birthday.text.toString()) == "success"){
                supportFragmentManager.popBackStack()
                return Toast.makeText(applicationContext,"correct", Toast.LENGTH_SHORT).show();
            }
        }else {
            return Toast.makeText(applicationContext, "รหัสผ่านไม่ตรงกัน", Toast.LENGTH_SHORT)
                .show();
        }




    }

    fun goadmin(item: MenuItem) {
        val intent = Intent(this, AdminActivity::class.java)
        startActivity(intent)
    }


}