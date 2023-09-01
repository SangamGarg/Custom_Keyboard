package com.sangam.keyboard

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.inputmethodservice.InputMethodService
import android.inputmethodservice.Keyboard
import android.inputmethodservice.KeyboardView
import android.os.Environment
import android.text.TextUtils
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import java.io.File
import java.io.FileOutputStream
import kotlin.random.Random


class MyInputMethodService : InputMethodService(), KeyboardView.OnKeyboardActionListener {

    private var keyboardView: KeyboardView? = null
    private var keyBoard: Keyboard? = null
    private var caps = false

    override fun onPress(primaryCode: Int) {
    }

    override fun onRelease(primaryCode: Int) {
    }

    override fun onKey(primaryCode: Int, keyCodes: IntArray?) {
        val inputConnection = currentInputConnection
        if (inputConnection != null) {
            when (primaryCode) {
                Keyboard.KEYCODE_DELETE -> {
                    val selectedText = inputConnection.getSelectedText(0)
                    if (TextUtils.isEmpty(selectedText)) {
                        inputConnection.deleteSurroundingText(1, 0)
                    } else {
                        inputConnection.commitText("", 1)
                    }
                    caps = !caps
                    keyBoard!!.setShifted(caps)
                    keyboardView!!.invalidateAllKeys()
                }
                Keyboard.KEYCODE_SHIFT -> {
                    caps = !caps
                    keyBoard!!.setShifted(caps)
                    keyboardView!!.invalidateAllKeys()
                }
                Keyboard.KEYCODE_DONE -> {

                    inputConnection.sendKeyEvent(
                    KeyEvent(
                        KeyEvent.ACTION_DOWN,
                        KeyEvent.KEYCODE_ENTER
                    )
                )}
                else -> {
                    var code = primaryCode.toChar()
                    if (Character.isLetter(code) && caps) {
                        code = code.uppercaseChar()
                    }
                    inputConnection.commitText(code.toString(), 1)
                }
            }
        }    }

    override fun onText(text: CharSequence?) {
    }

    override fun swipeLeft() {
    }

    override fun swipeRight() {
    }

    override fun swipeDown() {
    }

    override fun swipeUp() {
    }

    override fun onCreateInputView(): View {
        keyboardView = layoutInflater.inflate(R.layout.keyboard_view, null) as KeyboardView
        keyBoard = Keyboard(this, R.xml.keys_layout)
        keyboardView!!.keyboard = keyBoard
        keyboardView!!.setOnKeyboardActionListener(this)

        return keyboardView!!
    }

}