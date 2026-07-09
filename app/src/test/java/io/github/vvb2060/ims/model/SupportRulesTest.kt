package io.github.vvb2060.ims.model

import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class SupportRulesTest {
    @Test
    fun backupRestoreRequiresConfirmationWhenSimMccMncDiffers() {
        val backup = ConfigBackupSnapshot(
            id = "backup",
            name = "SIM",
            createdAtMillis = 1L,
            subId = 1,
            simTitle = "SIM",
            mcc = "460",
            mnc = "01",
            countryIso = "cn",
            featureValues = emptyMap(),
            countryMccOverride = "310",
        )

        assertFalse(SupportRules.requiresBackupMismatchConfirmation(backup, currentMcc = "460", currentMnc = "01"))
        assertTrue(SupportRules.requiresBackupMismatchConfirmation(backup, currentMcc = "310", currentMnc = "260"))
    }

    @Test
    fun apnDraftMustHaveValidMccMncBeforeConfirmation() {
        val valid = ApnDraftConfig("Carrier", "internet", "default,supl,ims", "460", "01")
        val invalid = ApnDraftConfig("Carrier", "internet", "default,supl,ims", "460", "")

        assertNull(SupportRules.validateApnDraft(valid))
        assertNotNull(SupportRules.validateApnDraft(invalid))
    }
}
