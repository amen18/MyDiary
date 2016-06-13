package com.Utilities;

/**
 * Created by AMEN on 03.06.2016.
 */
public enum Filter {

    UNLOCK(0) , LOCK(1), UNLOCK_PAGE(2) , LOCK_PAGE(3);

        private int f;

        private Filter(int f)
        {
            this.f = f;
        }

}
