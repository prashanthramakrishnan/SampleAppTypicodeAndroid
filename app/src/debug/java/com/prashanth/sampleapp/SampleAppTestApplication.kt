package com.prashanth.sampleapp

import com.prashanth.sampleapp.dependencyInjection.DaggerAppDaggerGraph
import com.prashanth.sampleapp.dependencyInjection.NetworkDaggerModule

class SampleAppTestApplication : SampleApplication() {

    override fun daggerComponent(application: SampleApplication): DaggerAppDaggerGraph.Builder {
        return super.daggerComponent(this)
            .networkDaggerModule(NetworkDaggerModule("http://localhost:8080/"))
    }
}