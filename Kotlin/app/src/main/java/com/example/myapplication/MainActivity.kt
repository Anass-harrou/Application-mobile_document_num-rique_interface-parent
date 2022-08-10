package com.example.myapplication

import android.app.DownloadManager
import android.content.*
import androidx.appcompat.app.*
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.facebook.login.LoginManager
import com.google.android.gms.auth.api.signin.*
import org.json.JSONArray

class MainActivity : AppCompatActivity() {
    val url:String = "http://172.24.96.1/API%20PHP/Operations/etudiantSort.php"
    var name: TextView? = null
    var niveau: TextView? = null
    var classe: TextView? = null
    var note: TextView? = null
    var presence: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        name = findViewById(R.id.txtnom)
        niveau = findViewById(R.id.txtniveau)
        classe = findViewById(R.id.txtclasse)
        note = findViewById(R.id.txtnote)
        presence = findViewById(R.id.txtpresence)
        getData()

        val btnlogout:RelativeLayout=findViewById(R.id.btnLogout)
        val btnallUsers:RelativeLayout=findViewById(R.id.btnallUsers)
        val btnNewUser:RelativeLayout=findViewById(R.id.btnNewUser)
        btnNewUser.setVisibility(View.VISIBLE)
        btnlogout.setVisibility(View.VISIBLE)
        btnallUsers.setVisibility(View.VISIBLE)
        val usertxt:TextView=findViewById(R.id.txtUser)
        val txttitl:TextView=findViewById(R.id.txttitl)

        val gso: GoogleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build()
        val mGoogleSignInClient: GoogleSignInClient = GoogleSignIn.getClient(this@MainActivity, gso);

        val nom=intent.getStringExtra("nom_parent")

        if(nom != null) usertxt.text=nom; else startActivity(Intent(this@MainActivity, LoginActivity::class.java))

        btnlogout.setOnClickListener(){
            mGoogleSignInClient.signOut().addOnCompleteListener{
                finish()
            }
            finish()
            usertxt.text=""
            txttitl.text=""
            btnlogout.setVisibility(View.INVISIBLE)
            btnallUsers.setVisibility(View.INVISIBLE)
            btnNewUser.setVisibility(View.INVISIBLE)
            LoginManager.getInstance().logOut();
        }
        btnNewUser.setOnClickListener{
            val intent=Intent(this@MainActivity,usermanager::class.java)
            intent.putExtra("Methode","Add User")
            startActivity(intent)
        }
        btnallUsers.setOnClickListener{startActivity(Intent(this@MainActivity,Listofusers::class.java))}

    }
    fun getData() {

        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(this)
        // Request a string response from the provided URL.
        val stringReq = StringRequest(
            Request.Method.GET,url,
            Response.Listener{ response ->
                val data = response.toString()
                val jArray = JSONArray(data)
                for(i in 0..jArray.length()-1){
                    var jobject = jArray.getJSONObject(i)
                    var nom = jobject.getString("nom")
                    var niv = jobject.getString("niveau")
                    var clas = jobject.getString("classe")
                    var not = jobject.getString("note")
                    var press = jobject.getString("presence")
                    name?.setText(nom.toString())
                    niveau?.setText(niv.toString())
                    classe?.setText(clas.toString())
                    note?.setText(not.toString())
                    presence?.setText(press.toString())
                    //date?.setText(Date.toString())
                } },
            Response.ErrorListener{ Log.d("API", "that didn't work") })
        queue.add(stringReq)
    }

}