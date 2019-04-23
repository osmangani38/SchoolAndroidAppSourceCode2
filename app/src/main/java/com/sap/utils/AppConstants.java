/*
 * Copyright (C) 2017 MINDORKS NEXTGEN PRIVATE LIMITED
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://mindorks.com/license/apache-v2
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */

package com.sap.utils;

/**
 * Created by amitshekhar on 08/01/17.
 */

public final class AppConstants {

    public static final String STATUS_CODE_SUCCESS = "success";
    public static final String STATUS_CODE_FAILED = "failed";
    public static final String WS_BASE_URL = "http";
    public static final String Loading = "Loading...";

    public static final String WS_METHOD_SIGNUP = "http";
    public static final String WS_METHOD_SIGNIN = "http";
   //public static final String BaseURL = "http://banglarsiksha.in/sms/ws/api/";
    public static final String BaseURL = "https://school.banglarshiksha.gov.in/sms/ws/api/";

    public static final String WS_StudentsDetails = "http://ehrms.wbsed.gov.in/sims_new/Web_service/student_all_details?token=ba0b38a250289cc85a5ad60ca7712954&school_code=112317";
    public static final int API_STATUS_CODE_LOCAL_ERROR = 0;

    public static final String DB_NAME = "mindorks_mvp.db";
    public static final String PREF_NAME = "mindorks_pref";

    public static final long NULL_INDEX = -1L;

    public static final String SEED_DATABASE_OPTIONS = "seed/options.json";
    public static final String SEED_DATABASE_QUESTIONS = "seed/questions.json";

    public static final String TIMESTAMP_FORMAT = "yyyyMMdd_HHmmss";

    public static String GLOBAL_TOPIC_ID = "";
    public static String SUBJECT_LESSON_ID = "";

    private AppConstants() {
        // This utility class is not publicly instantiable
    }
}
