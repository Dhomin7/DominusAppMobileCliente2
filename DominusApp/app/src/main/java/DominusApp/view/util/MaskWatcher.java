package DominusApp.view.util;

import android.text.Editable;
import android.text.TextWatcher;

public class MaskWatcher implements TextWatcher {
    private boolean isRunning = false;
    private boolean isDeleting = false;
    private final String mask;

    public MaskWatcher(String mask) {
        this.mask = mask;
    }

    public static MaskWatcher mascaraCpf() {
        return new MaskWatcher("###.###.###-##");
    }
    @Override
    public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
        this.isDeleting = count > after;
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        if (isRunning || isDeleting)
            return;

        isRunning = true;

        int tamanhoEditable = editable.length();
        if (tamanhoEditable < mask.length()){
            if (mask.charAt(tamanhoEditable) != '#')
                editable.append(mask.charAt(tamanhoEditable));
            else if (mask.charAt(tamanhoEditable - 1) != '#')
                editable.insert(tamanhoEditable - 1, mask, tamanhoEditable - 1, tamanhoEditable);
        }
        isRunning = false;
    }
}
