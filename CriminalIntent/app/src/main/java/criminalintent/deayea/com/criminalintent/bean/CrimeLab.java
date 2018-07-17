package criminalintent.deayea.com.criminalintent.bean;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by danish on 2018/5/31.
 */

public class CrimeLab {


    private static CrimeLab sCrimeLab;



    private List<Crime> mCrimes;

    public static CrimeLab getsCrimeLab(Context context) {
        if (sCrimeLab == null){
            sCrimeLab = new CrimeLab(context);
        }
        return sCrimeLab;
    }

    public CrimeLab(Context context) {
        mCrimes = new ArrayList<>();
        for (int i = 0;i<100;i++){
            Crime crime = new Crime();
            crime.setmTitle("Crime #" + i);
            crime.setmSolved(i%2 == 0);
            mCrimes.add(crime);
        }
    }

    public List<Crime> getmCrimes() {
        return mCrimes;
    }

    public Crime getCrime(UUID uuid) {
        for (Crime crime:mCrimes){
            if (crime.getmId().equals(uuid)){
                return crime;
            }
        }
        return null;
    }
}
