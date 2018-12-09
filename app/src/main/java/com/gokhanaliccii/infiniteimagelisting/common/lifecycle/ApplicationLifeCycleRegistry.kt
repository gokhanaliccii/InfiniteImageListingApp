package com.gokhanaliccii.infiniteimagelisting.common.lifecycle

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager
import com.gokhanaliccii.infiniteimagelisting.InfiniteImageListingApp

class ApplicationLifeCycleRegistry {

    fun registerAppLifeCycleCallback(application: Application) {
        application.registerActivityLifecycleCallbacks(
                object : Application.ActivityLifecycleCallbacks {
                    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                        InfiniteImageListingApp.activityLifeCycleBag.lifeCycleEventTriggered("onActivityCreated")
                        handleActivity(activity)
                    }

                    override fun onActivityStarted(activity: Activity) {
                        InfiniteImageListingApp.activityLifeCycleBag.lifeCycleEventTriggered("onActivityStarted")
                    }

                    override fun onActivityResumed(activity: Activity) {
                        InfiniteImageListingApp.activityLifeCycleBag.lifeCycleEventTriggered("onActivityResumed")
                    }

                    override fun onActivityPaused(activity: Activity) {
                        InfiniteImageListingApp.activityLifeCycleBag.lifeCycleEventTriggered("onActivityPaused")
                    }

                    override fun onActivityStopped(activity: Activity) {
                        InfiniteImageListingApp.activityLifeCycleBag.lifeCycleEventTriggered("onActivityStopped")
                    }

                    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle?) {
                        InfiniteImageListingApp.activityLifeCycleBag.lifeCycleEventTriggered("onActivitySaveInstanceState")
                    }

                    override fun onActivityDestroyed(activity: Activity) {
                        InfiniteImageListingApp.activityLifeCycleBag.lifeCycleEventTriggered("onActivityDestroyed")
                    }
                })
    }

    private fun handleActivity(activity: Activity) {
        if (activity is FragmentActivity) {
            activity.supportFragmentManager.registerFragmentLifecycleCallbacks(
                    object : FragmentManager.FragmentLifecycleCallbacks() {

                        override fun onFragmentCreated(fm: FragmentManager, f: Fragment, savedInstanceState: Bundle?) {
                            super.onFragmentCreated(fm, f, savedInstanceState)
                            InfiniteImageListingApp.fragmentLifeCycleBag.lifeCycleEventTriggered("onFragmentCreated")
                        }

                        override fun onFragmentResumed(fm: FragmentManager, f: Fragment) {
                            super.onFragmentResumed(fm, f)
                            InfiniteImageListingApp.fragmentLifeCycleBag.lifeCycleEventTriggered("onFragmentResumed")
                        }

                        override fun onFragmentStopped(fm: FragmentManager, f: Fragment) {
                            super.onFragmentStopped(fm, f)
                            InfiniteImageListingApp.fragmentLifeCycleBag.lifeCycleEventTriggered("onFragmentStopped")
                        }

                        override fun onFragmentDestroyed(fm: FragmentManager, f: Fragment) {
                            super.onFragmentDestroyed(fm, f)
                            InfiniteImageListingApp.fragmentLifeCycleBag.lifeCycleEventTriggered("onFragmentDestroyed")
                        }

                    }, true)
        }
    }
}