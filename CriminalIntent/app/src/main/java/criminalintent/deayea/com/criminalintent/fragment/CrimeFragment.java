package criminalintent.deayea.com.criminalintent.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import criminalintent.deayea.com.criminalintent.R;
import criminalintent.deayea.com.criminalintent.bean.Crime;

/**
 * Created by danish on 2018/5/31.
 */

public class CrimeFragment extends Fragment {
    @BindView(R.id.crime_title)
    EditText crimeTitle;
    @BindView(R.id.crime_date)
    Button crimeDate;
    @BindView(R.id.crime_solved)
    CheckBox crimeSolved;
    Unbinder unbinder;
    private Crime mCrime;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_crime, container, false);
        unbinder = ButterKnife.bind(this, view);

        mCrime = new Crime();

        crimeTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mCrime.setmTitle(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        crimeDate.setText(mCrime.getmDate().toString());
        crimeDate.setEnabled(false);
        crimeSolved.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                crimeSolved.setChecked(isChecked);
            }
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
