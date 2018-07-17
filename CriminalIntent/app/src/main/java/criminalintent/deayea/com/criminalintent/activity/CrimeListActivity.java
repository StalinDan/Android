package criminalintent.deayea.com.criminalintent.activity;

import android.support.v4.app.Fragment;

import criminalintent.deayea.com.criminalintent.fragment.CrimeListFragment;
import criminalintent.deayea.com.criminalintent.fragment.SingleFragmentActivity;

/**
 * Created by danish on 2018/5/31.
 */

public class CrimeListActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new CrimeListFragment();
    }
}
