package com.aplication.myuniversity.entry;

import android.provider.BaseColumns;

public class StudentSubjectEntry implements BaseColumns {
    public static final String TABLE_NAME = "TABLE_STUDENT_SUBJECT";
    public static final String PARENT_STUDENT = "ParentStudent";
    public static final String SUBJECT_ID = "SubjectId";
    public static final String SCORES = "Scores";
}
