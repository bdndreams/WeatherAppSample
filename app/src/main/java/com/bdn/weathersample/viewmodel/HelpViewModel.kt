package com.bdn.weathersample.viewmodel

import androidx.lifecycle.ViewModel

class HelpViewModel:ViewModel() {

    val htmlDocument =
        "<html><body><h1>Help to use the Application</h1><p>Go to Add city, Drag the marker to select the City and click save button to add city on your bookmark page.</p>" +
                "<p>You can delete the bookmarked city by pressing delete button on bookmark page.</p>" +
                "<p>By clicking on city card, you can find the Weather details.</p></body></html>"
}