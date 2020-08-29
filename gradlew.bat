/*
 * Copyright (C) 2019 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.server.locksettings.recoverablekeystore.storage;

import android.content.Context;
import android.os.ServiceSpecificException;
import android.os.UserHandle;
import android.os.UserManager;
import android.util.Log;

import com.android.internal.annotations.VisibleForTesting;
import com.android.server.locksettings.recoverablekeystore.WrappedKey;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Cleans up data when user is removed.
 */
public class CleanupManager {
    private static final String TAG = "CleanupManager";

    private final Context mContext;
    private final UserManager mUserManager;
    private final RecoverableKeyStoreDb mDatabase;
    private final RecoverySnapshotStorage mSnapshotStorage;
    private final ApplicationKeyStorage mApplicationKeyStorage;

    // Serial number can not be changed at runtime.
    private Map<Integer, Long> mSerialNumbers; // Always in sync with the database.

    /**
     * Creates a new instance of the class.
     * IMPORTANT: {@code verifyKnownUsers} must be called before the first data access.
     */
    public static CleanupManager getInstance(
            Context context,
            RecoverySnapshotStorage snapshotStorage,
            RecoverableKeyStoreDb recoverableKeyStoreDb,
            ApplicationKeyStorage applicationKeyStorage) {
        return new CleanupManager(
                context,
                snapshotStorage,
                recoverableKeyStoreDb,
                UserManager.get(context),
                applicationKeyStorage);
    }

    @VisibleForTesting
    CleanupManager(
            Conte