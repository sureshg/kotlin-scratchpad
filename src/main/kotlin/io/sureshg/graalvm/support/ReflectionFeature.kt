package io.sureshg.graalvm.support

import io.github.classgraph.ClassGraph
import org.graalvm.nativeimage.hosted.Feature
import org.graalvm.nativeimage.hosted.Feature.BeforeAnalysisAccess
import org.graalvm.nativeimage.hosted.RuntimeReflection

/**
 * GraalVM native-image feature implementation class to register the
 * annotation processor (`kapt`) generated classed for Moshi.
 */
class ReflectionFeature : Feature {

    private fun allowInstantiate(cl: Class<*>) {
        println("Adding constructors for $cl")
        RuntimeReflection.register(cl)
        for (c in cl.constructors) {
            RuntimeReflection.register(c)
        }
    }

    private fun allowMethods(cl: Class<*>) {
        println("Adding methods for $cl")
        for (method in cl.methods) {
            RuntimeReflection.register(method)
        }
    }

    private fun allowFields(cl: Class<*>) {
        println("Adding fields for $cl")
        for (field in cl.fields) {
            RuntimeReflection.register(field)
        }
    }

    override fun beforeAnalysis(access: BeforeAnalysisAccess) {
        try {
            ClassGraph().enableAllInfo().scan().use { scanResult ->
                val moshiAdapters = scanResult.getSubclasses("com.squareup.moshi.JsonAdapter")
                val adapterClasses = moshiAdapters.loadClasses()
                adapterClasses.forEach {
                    allowInstantiate(it)
                }
            }
        } catch (t: Throwable) {
            throw RuntimeException("Unable to analyse classes", t)
        }
    }
}