/*
 * @author: Nagabhushan S B
 * This class is an implementation of the BackupAgentHelper which enables the app to restore 
 * data such user settings, preferences, high scores, etc. In the Label The Diagram App, this can be used to
 * backup and store high scores, statistics, etc safely on a backend.
 * The following snippet needs to be added to the Manifest:
 * 
 * <application android:label="MyApp"
             android:backupAgent="TheBackupAgent">
       ...
       <meta-data android:name="com.google.android.backup.api_key"
                  android:value="ABcDe1FGHij2KlmN3oPQRs4TUvW5xYZ" />
       ...
   </application>
   
 * To request a backup we use the following snippet:
 * 
 *  import android.app.backup.BackupManager;
    ...

    public void requestBackup() {
           BackupManager bm = new BackupManager(this);
           bm.dataChanged();
    }
 *
 */


package com.example.illustrativeproject;

import android.app.backup.BackupAgentHelper;
import android.app.backup.SharedPreferencesBackupHelper;

public class TheBackupAgent extends BackupAgentHelper {
	
	// The names of the SharedPreferences groups that the application maintains.
	// These
	// are the same strings that are passed to getSharedPreferences(String,
	// int).
	static final String PREFS_DISPLAY = "displayprefs";
	static final String PREFS_SCORES = "highscores";

	// An arbitrary string used within the BackupAgentHelper implementation to
	// identify the SharedPreferencesBackupHelper's data.
	static final String MY_PREFS_BACKUP_KEY = "myprefs";

	// Simply allocate a helper and install it
	public void onCreate() {
		SharedPreferencesBackupHelper helper = new SharedPreferencesBackupHelper(
				this, PREFS_DISPLAY, PREFS_SCORES);
		addHelper(MY_PREFS_BACKUP_KEY, helper);
	}
}