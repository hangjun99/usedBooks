package com.example.booksale

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject


class MainActivity : AppCompatActivity() {
    lateinit var ID: EditText
    lateinit var Password: EditText
    lateinit var Login: Button
    lateinit var Signup: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ID = findViewById<EditText>(R.id.Put_ID)
        Password = findViewById<EditText>(R.id.Put_Pass)
        Login = findViewById<Button>(R.id.Login_btn)
        Signup = findViewById<Button>(R.id.Register_btn)

        //로그인 버튼 이벤트
        Login.setOnClickListener(View.OnClickListener {
            val id = ID.getText().toString()
            val pw = Password.getText().toString()
            val responseListener =
                Response.Listener<String?> { response ->
                    try {
                        val jsonObject = JSONObject(response)
                        val success = jsonObject.getBoolean("success")
                        if (success) {
                            val NickName = jsonObject.getString("nickName")
                            val UserInd = jsonObject.getInt("userInd")
                            Toast.makeText(
                                applicationContext,
                                "로그인 성공. ID :$NickName , $UserInd",
                                Toast.LENGTH_SHORT
                            ).show()
                            Log.d("LoginActivity", "로그인 성공")
                            val intent = Intent(this@MainActivity,LoginActivity::class.java)
                            intent.putExtra("UserInd", UserInd)
                            intent.putExtra("NickName", NickName)
                            startActivity(intent)
                            finish()
                        } else {
                            Toast.makeText(applicationContext, "실패", Toast.LENGTH_SHORT).show()
                            return@Listener
                        }
                    } catch (e: JSONException) {
                        e.printStackTrace()
                        Toast.makeText(applicationContext, "예외 1", Toast.LENGTH_SHORT).show()
                        return@Listener
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            val loginRequestActivity = LoginRequestActivity(id, pw, responseListener)
            val queue = Volley.newRequestQueue(applicationContext)
            queue.add(loginRequestActivity)
        })


        //회원가입 버튼 이벤트
        Signup.setOnClickListener(View.OnClickListener {
            try {
                val intent = Intent(applicationContext, SignupActivity::class.java)
                startActivity(intent)
            }   catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(applicationContext, "예외 발생: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}