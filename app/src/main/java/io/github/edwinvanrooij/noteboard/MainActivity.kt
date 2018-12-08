package io.github.edwinvanrooij.noteboard

import android.app.Activity
import android.os.Bundle
import android.widget.Toast

class MainActivity : Activity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    val g = GameEngine()

    val s = g.getSomething()

    Toast.makeText(this, s, Toast.LENGTH_LONG).show()
  }
}
