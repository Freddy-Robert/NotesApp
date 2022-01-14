package com.app.notes.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Note {

    @PrimaryKey(autoGenerate = true)
    int id;

    @ColumnInfo
    String name;

    @ColumnInfo
    String description;

    @ColumnInfo
    int importance;

    @ColumnInfo
    boolean isCompleted;

    public Note(String name, String description, int importance, boolean isCompleted) {
        this.name = name;
        this.description = description;
        this.importance = importance;
        this.isCompleted = isCompleted;
    }

    @Ignore
    public Note(int id, String name, String description, int importance, boolean isCompleted) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.importance = importance;
        this.isCompleted = isCompleted;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getImportance() {
        return importance;
    }

    public void setImportance(int importance) {
        this.importance = importance;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }
}
