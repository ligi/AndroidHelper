package org.ligi.axt.test;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ligi.axt.AXT;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.fail;

@RunWith(RobolectricTestRunner.class)
public class TheEditTextAXT {
    private final static String TEXT2SET = "foobar";

    @Test
    public void should_set_text_when_needed() {

        EditText editText = new EditText(RuntimeEnvironment.application);

        AXT.at(editText).changeTextIfNeeded(TEXT2SET);

        assertEquals(editText.getText().toString(), TEXT2SET);
    }


    @Test
    public void should_not_set_text_when_not_needed() {

        EditText editText = new EditText(RuntimeEnvironment.application);

        editText.setText(TEXT2SET);

        // Assert - should not fire
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                fail();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                fail();
            }

            @Override
            public void afterTextChanged(Editable s) {
                fail();
            }
        });

        AXT.at(editText).changeTextIfNeeded(TEXT2SET);

    }
}
