package com.example.iris

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.iris.ml.Speices2
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.nio.ByteBuffer

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val plength: EditText =findViewById(R.id.petallength)
        val pwidth: EditText =findViewById(R.id.petalwidth)
        val slength: EditText =findViewById(R.id.sepallength)
        val swidth: EditText =findViewById(R.id.sepalwidth)
        val button: Button =findViewById(R.id.button)

        button.setOnClickListener {
            val petallength=plength.text.toString().toFloat()
            val petalwidth=pwidth.text.toString().toFloat()
            val sepallength=slength.text.toString().toFloat()
            val sepalwidth=swidth.text.toString().toFloat()
            Toast.makeText(this,"helloo guys",Toast.LENGTH_SHORT).show()

            val model = Speices2.newInstance(this)

            val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 4), DataType.FLOAT32)

            val byteBuffer: ByteBuffer = ByteBuffer.allocateDirect(4*4)
            byteBuffer.putFloat(petallength)
            byteBuffer.putFloat(petalwidth)
            byteBuffer.putFloat(sepallength)
            byteBuffer.putFloat(sepalwidth)
            inputFeature0.loadBuffer(byteBuffer)

            val outputs = model.process(inputFeature0)
            val outputFeature0 = outputs.outputFeature0AsTensorBuffer.floatArray
            val textview: TextView =findViewById(R.id.textView2)
            textview.text = "1- "+outputFeature0[0].toString()+'\n'+
                            "2- "+outputFeature0[1].toString()+'\n'+
                             "3- "+outputFeature0[2].toString()


            model.close()

        }
    }
}