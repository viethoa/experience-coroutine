package com.example.coroutine

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        FanInDesignPattern().execute(lifecycleScope)
        //FanOutDesignPattern().execute(lifecycleScope)
        //RacingDesignPattern().execute(lifecycleScope)
        //SideKickDesignPattern().execute(lifecycleScope)
        //PipelineDesignPattern().execute(lifecycleScope)
        //DeferralDesignPattern().execute(lifecycleScope)
        //TestFlowBuffering().execute(lifecycleScope)

//        lifecycleScope.launch {
//            val temp = TestLiveDataFilter()
//            temp.execute(this@MainActivity)
//            delay(500)
//            temp.liveData.value = 2
//            delay(500)
//            temp.liveData.value = 3
//        }

        //CommandDesignPattern().execute()
        //DemoIterator().execute()
//        CoroutineScope(Dispatchers.Main).launch {
//            DemoFlow().execute()
//        }
    }
}