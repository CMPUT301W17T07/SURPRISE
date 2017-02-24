package com.cmput301w17t07.moody;

import android.media.Image;

/**
 * Created by mike on 2017-02-23.
 */

public class User {

    public String username;
    public Image profilePicture;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Image getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(Image profilePicture) {
        // Need to check image's size. Separate class object for this?
        this.profilePicture = profilePicture;
    }
}
