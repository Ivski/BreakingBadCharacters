package rocks.ivski.bbc

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import rocks.ivski.bbc.ui.list.ListFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, ListFragment())
            .commit()
    }
}