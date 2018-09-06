package eu.europeana.corelib.utils;

import eu.europeana.corelib.edm.utils.ValidateUtils;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Patrick Ehlert
 * Created on 09-07-2018
 */
public class ValidateUtilsTest {

    @Test
    public void testRecordId() {
        assertTrue(ValidateUtils.validateRecordIdFormat("/2023006/24062A51_priref_16913"));
    }

    @Test
    public void testRecordIdFalseSpace() {
        assertFalse(ValidateUtils.validateRecordIdFormat("/2023006/24062A51 priref_16913"));
    }

    @Test
    public void testRecordIdFalseMissingSlashFront() {
        assertFalse(ValidateUtils.validateRecordIdFormat("2023006/24062A51_priref_16913"));
    }

    @Test
    public void testRecordIdFalseMissingSlashMiddle() {
        assertFalse(ValidateUtils.validateRecordIdFormat("/2023006"));
    }


}
