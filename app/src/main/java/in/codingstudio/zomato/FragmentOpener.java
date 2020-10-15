package in.codingstudio.zomato;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class FragmentOpener {

    public FragmentOpener(Context context)
    {
        this.context=context;
    }
    public void SimpleAdd(FragmentManager fragmentManager, int containerID, Fragment fragment)
    {
        FragmentManager manager= fragmentManager;
        FragmentTransaction transaction=manager.beginTransaction();
        transaction.add(containerID,fragment,"");
        transaction.commit();
    }
    public void replace(FragmentManager fragmentManager, int containerID, Fragment fragment)
    {
        FragmentManager manager= fragmentManager;
        FragmentTransaction transaction=manager.beginTransaction();
        transaction.replace(containerID,fragment,"");
        transaction.commit();
    }
    public void replace_With_BackStack(FragmentManager fragmentManager, int containerID, Fragment fragment)
    {
        String backStack=fragment.getClass().getName().toUpperCase();
        FragmentManager manager= fragmentManager;
        FragmentTransaction transaction=manager.beginTransaction();
        transaction.addToBackStack(backStack);
        transaction.replace(containerID,fragment,"");
        transaction.commit();
    }

    public void SimpleAdd(FragmentManager fragmentManager, int containerID, Fragment fragment, String tag)
    {
        FragmentManager manager         =   fragmentManager;
        FragmentTransaction transaction =   manager.beginTransaction();
        transaction.add(containerID,fragment,tag);
        transaction.commit();
    }

    public void replace(FragmentManager fragmentManager, int containerID, Fragment fragment, String tag)
    {
        FragmentManager manager         =   fragmentManager;
        FragmentTransaction transaction =   manager.beginTransaction();
        transaction.replace(containerID,fragment,tag);
        transaction.commit();
    }

    public void replace_With_BackStack(FragmentManager fragmentManager, int containerID, Fragment fragment, String tag)
    {
        String backStack                =   fragment.getClass().getName().toUpperCase();
        FragmentManager manager         =   fragmentManager;
        FragmentTransaction transaction =   manager.beginTransaction();
        transaction.addToBackStack(backStack);
        transaction.replace(containerID,fragment,tag);
        transaction.commit();
    }

    public void replace_With_Bottom_To_Top_Animation(FragmentManager fragmentManager, int containerID, Fragment fragment, String tag, int enter_xml_id, int exit_xml_id)
    {
        FragmentManager manager         =   fragmentManager;
        FragmentTransaction transaction =   manager.beginTransaction();
        transaction.setCustomAnimations(enter_xml_id,exit_xml_id,enter_xml_id,exit_xml_id);
        transaction.replace(containerID,fragment,tag);
        transaction.commit();
    }

    public void remove(FragmentManager fragmentManager, Fragment fragment)
    {
        //String backStack=fragment.getClass().getName().toUpperCase();
        FragmentTransaction transaction= fragmentManager.beginTransaction();
        //transaction.addToBackStack(backStack);
        transaction.remove(fragment);
        transaction.commit();
    }


//--------------------------------------------------------------------------------------------------
Context context;
}
