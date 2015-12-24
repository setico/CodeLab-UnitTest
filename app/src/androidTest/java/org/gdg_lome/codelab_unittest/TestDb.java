
package org.gdg_lome.codelab_unittest;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;

import org.gdg_lome.codelab_unittest.data.ProgrammeDbHelper;
import org.gdg_lome.codelab_unittest.data.ProgrammeContract.MatiereEntry;

public class TestDb extends AndroidTestCase {

    public static final String LOG_TAG = TestDb.class.getSimpleName();
    static final String TEST_MATIERE_NOM= "Algorithme";
    static final String TEST_MATIERE_DESCRIPTION= "Algorithme test case";
    static final String TEST_MATIERE_LOGO = "http://gdglome/cictogo/algo.png";

    //test de creation de la base de donnees
    public void testACreateDb() throws Throwable {
        mContext.deleteDatabase(ProgrammeDbHelper.DB_NAME);
        SQLiteDatabase db = new ProgrammeDbHelper(this.mContext).getWritableDatabase();
        assertEquals(true, db.isOpen());
        db.close();
    }

    //test d'insertion de donnee
    public void testBInsert(){
        ProgrammeDbHelper dbHelper = new ProgrammeDbHelper(mContext);
        long matiereRowId  = dbHelper.insert(createMatiereValues());
        assertTrue(matiereRowId != -1);
    }

    //test de selection de donnees
    public void testCGet() {
        ProgrammeDbHelper dbHelper = new ProgrammeDbHelper(mContext);
        validateCursor(dbHelper.get(), createMatiereValues());
    }

    //test de suppression de donnee
    public void testDDelete(){
        ProgrammeDbHelper dbHelper = new ProgrammeDbHelper(mContext);
        assertTrue(dbHelper.delete() > 0);
    }

    // les valeurs du test
    static ContentValues createMatiereValues() {
        ContentValues testValues = new ContentValues();
        testValues.put(MatiereEntry.COLUMN_MATIERE_LOGO, TEST_MATIERE_LOGO);
        testValues.put(MatiereEntry.COLUMN_MATIERE_NOM, TEST_MATIERE_NOM);
        testValues.put(MatiereEntry.COLUMN_MATIERE_DESCRIPTION, TEST_MATIERE_DESCRIPTION);
        return testValues;
    }

    //verification des valeurs du curseur
    static void validateCursor(Cursor valueCursor, ContentValues expectedValues) {

        assertTrue(valueCursor.moveToFirst());
        assertEquals(valueCursor.getString(valueCursor.getColumnIndex(MatiereEntry.COLUMN_MATIERE_LOGO)),
                expectedValues.getAsString(MatiereEntry.COLUMN_MATIERE_LOGO));
        assertEquals(valueCursor.getString(valueCursor.getColumnIndex(MatiereEntry.COLUMN_MATIERE_NOM)),
                expectedValues.getAsString(MatiereEntry.COLUMN_MATIERE_NOM));
        assertEquals(valueCursor.getString(valueCursor.getColumnIndex(MatiereEntry.COLUMN_MATIERE_DESCRIPTION)),
                expectedValues.getAsString(MatiereEntry.COLUMN_MATIERE_DESCRIPTION));
        valueCursor.close();
    }
}
