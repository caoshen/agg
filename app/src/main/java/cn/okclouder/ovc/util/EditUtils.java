package cn.okclouder.ovc.util;

import android.content.Context;
import android.widget.EditText;

import cn.okclouder.ovc.R;
import cn.okclouder.library.util.StringUtils;

public class EditUtils {

    public static boolean checkContentValid(Context context, EditText... editTexts) {
        if (editTexts == null || editTexts.length == 0) {
            return false;
        }
        for (EditText editText : editTexts) {
            String text = editText.getText().toString();
            if (StringUtils.isBlank(text)) {
                editText.setError(context.getString(R.string.tips_content_is_empty));
                return false;
            }
        }
        return true;
    }
}
