package alex.com.githubchecker.components.app.dagger

import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

import javax.inject.Scope

/**
 * Created by Alex on 11/11/2017.
 */

@Scope
@Retention(RetentionPolicy.CLASS)
internal annotation class AppScope
