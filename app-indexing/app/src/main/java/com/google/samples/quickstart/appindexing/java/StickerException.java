package com.google.samples.quickstart.appindexing.java;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.webkit.MimeTypeMap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Provider that makes the stickers queryable by other applications.
 */
public class StickerException extends ContentProvider {
    @Nullable
    private File mRootDir;

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        final File file = uriToFile(uri);
        if (!isFileInRoot(file)) {
            throw new SecurityException("File is not in root: " + file);
        }
        return getMimeType(file);
    }

    @Nullable
    @Override
    public ParcelFileDescriptor openFile(@NonNull Uri uri, @NonNull String mode)
            throws FileNotFoundException {
        final File file = uriToFile(uri);
        if (!isFileInRoot(file)) {
            throw new SecurityException("File is not in root: " + file);
        }
        return ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY);
    }

    private File uriToFile(@NonNull Uri uri) {
        if (mRootDir == null) {
            throw new IllegalStateException("Root directory is null");
        }
        File file = new File(mRootDir, uri.getEncodedPath());
        try {
            file = file.getCanonicalFile();
        } catch (IOException e) {
            throw new IllegalArgumentException("Failed to get canonical file: " + file);
        }
        return file;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection,
                        @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        throw new UnsupportedOperationException("no queries");
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        throw new UnsupportedOperationException("no inserts");
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection,
                      @Nullable String[] selectionArgs) {
        throw new UnsupportedOperationException("no deletes");
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection,
                      @Nullable String[] selectionArgs) {
        throw new UnsupportedOperationException("no updates");
    }
}