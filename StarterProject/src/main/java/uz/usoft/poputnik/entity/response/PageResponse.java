package uz.usoft.poputnik.entity.response;

import com.mikepenz.fastadapter.IItem;

import java.util.ArrayList;

/**
 * Created by adizbek on 24.07.2018.
 */

public class PageResponse<T> {
    public int current_page;
    public ArrayList<T> data;
}
