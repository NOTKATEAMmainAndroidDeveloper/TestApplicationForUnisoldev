package com.ntk.testapplicationforunisoldev.states

sealed class MakePhotoState {
    object NonAction : MakePhotoState()
    object TakePhoto : MakePhotoState()
    object MakePhoto : MakePhotoState()
}