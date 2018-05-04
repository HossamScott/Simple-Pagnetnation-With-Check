package com.m.hossam.hossamgithub.Items;

public class Main_Activity_Item {
    String Id;
    String Title;
    String Album_id;
    String photo;

    public String getFiltered() {
        return filtered;
    }

    public void setFiltered(String filtered) {
        this.filtered = filtered;
    }

    String filtered = "hide";

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getAlbum_id() {
        return Album_id;
    }

    public void setAlbum_id(String album_id) {
        Album_id = album_id;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }


    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public boolean checked = false;
}
