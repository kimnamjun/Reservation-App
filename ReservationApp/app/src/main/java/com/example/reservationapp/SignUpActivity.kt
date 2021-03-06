package com.example.reservationapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.reservationapp.DbStructure.UserInfo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_signup.*

class SignUpActivity : AppCompatActivity() {

    var id = "id"
    var password = "pw"
    var passwordConfirm = "pwc"

    val database = FirebaseDatabase.getInstance()
    val mAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        // validation
        button_signUpActSignUp.setOnClickListener {
            id = editText_signUpActID.text.toString()
            password = editText_signUpActPW.text.toString()
            passwordConfirm = editText_signUpActPWConfirm.text.toString()

            if(id.isBlank() || password.isBlank() || passwordConfirm.isBlank()){
                Toast.makeText(this@SignUpActivity, "이메일과 비밀번호를 입력하세요.", Toast.LENGTH_SHORT).show()
            }
            else if(!android.util.Patterns.EMAIL_ADDRESS.matcher(id).matches()){
                Toast.makeText(this@SignUpActivity, "이메일 주소를 확인하세요.", Toast.LENGTH_SHORT).show()
            }
            else if(password.length < 6 || passwordConfirm.length < 6){
                Toast.makeText(this@SignUpActivity, "비밀 번호를 6자 이상 입력하세요.", Toast.LENGTH_SHORT).show()
            }
            else if(password != passwordConfirm){
                Toast.makeText(this@SignUpActivity, "비밀번호가 서로 다릅니다.", Toast.LENGTH_SHORT).show()
            }
            else{
                createUserAccount(id, password)
                finish()
            }
        }
    }

    private fun createUserAccount(id: String, password: String) {
        mAuth.createUserWithEmailAndPassword(id, password).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                Toast.makeText(this@SignUpActivity, "회원 가입이 정상 처리 되었습니다.", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this@SignUpActivity, "회원 가입 실패 (Firebase)", Toast.LENGTH_SHORT).show()
            }
        }

        // 데이터베이스에 추가
        val myRef = database.getReference("UserList")
        var userInfo = UserInfo(id)
        myRef.child(userInfo.removeAt()).setValue(userInfo)
    }
}