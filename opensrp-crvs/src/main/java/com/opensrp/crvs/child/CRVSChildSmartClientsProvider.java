package com.opensrp.crvs.child;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.ei.opensrp.commonregistry.AllCommonsRepository;
import org.ei.opensrp.commonregistry.CommonPersonObject;
import org.ei.opensrp.commonregistry.CommonPersonObjectClient;
import org.ei.opensrp.cursoradapter.SmartRegisterCLientsProviderForCursorAdapter;
import org.ei.opensrp.domain.Alert;

import com.opensrp.crvs.R;
import com.opensrp.crvs.application.CRVSApplication;
import org.ei.opensrp.service.AlertService;
import org.ei.opensrp.view.contract.SmartRegisterClient;
import org.ei.opensrp.view.contract.SmartRegisterClients;
import org.ei.opensrp.view.customControls.CustomFontTextView;
import org.ei.opensrp.view.dialog.FilterOption;
import org.ei.opensrp.view.dialog.ServiceModeOption;
import org.ei.opensrp.view.dialog.SortOption;
import org.ei.opensrp.view.viewHolder.OnClickFormLauncher;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static org.ei.opensrp.util.StringUtil.humanize;

/**
 * Created by user on 2/12/15.
 */
public class CRVSChildSmartClientsProvider implements SmartRegisterCLientsProviderForCursorAdapter {

    private final LayoutInflater inflater;
    private final Context context;
    private final View.OnClickListener onClickListener;

    private final int txtColorBlack;
    private final AbsListView.LayoutParams clientViewLayoutParams;
    private final AlertService alertService;


    public CRVSChildSmartClientsProvider(Context context,
                                         View.OnClickListener onClickListener,
                                         AlertService alertService) {
        this.onClickListener = onClickListener;
        this.alertService = alertService;
        this.context = context;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        clientViewLayoutParams = new AbsListView.LayoutParams(MATCH_PARENT,
                (int) context.getResources().getDimension(org.ei.opensrp.R.dimen.list_item_height));
        txtColorBlack = context.getResources().getColor(org.ei.opensrp.R.color.text_black);
    }

    @Override
    public void getView(final SmartRegisterClient smartRegisterClient, View convertView) {
        View itemView = convertView;

        LinearLayout profileinfolayout = (LinearLayout)itemView.findViewById(R.id.profile_info_layout);

        ImageView profilepic = (ImageView)itemView.findViewById(R.id.profilepic);
        TextView mothername = (TextView)itemView.findViewById(R.id.mother_name);
        TextView fathername = (TextView)itemView.findViewById(R.id.father_name);
        TextView name = (TextView)itemView.findViewById(R.id.name);
        TextView placeofbirth = (TextView)itemView.findViewById(R.id.placeofbirth);
        TextView dateofbirth = (TextView)itemView.findViewById(R.id.dateofbirth);
        TextView age = (TextView)itemView.findViewById(R.id.age);
        TextView nid = (TextView)itemView.findViewById(R.id.nid);



        TextView address = (TextView)itemView.findViewById(R.id.address);
////        Button due_visit_date = (Button)itemView.findViewById(R.id.hh_due_date);
//
//        ImageButton follow_up = (ImageButton)itemView.findViewById(R.id.btn_edit);
        profileinfolayout.setOnClickListener(onClickListener);
        profileinfolayout.setTag(smartRegisterClient);

        final CommonPersonObjectClient pc = (CommonPersonObjectClient) smartRegisterClient;

        fathername.setText(humanize(pc.getColumnmaps().get("father_name_english")!=null?pc.getColumnmaps().get("father_name_english"):""));
        mothername.setText(humanize(pc.getColumnmaps().get("mother_name_english") != null ? pc.getColumnmaps().get("mother_name_english"):""));
        name.setText(humanize(pc.getColumnmaps().get("name_Fname")!=null?pc.getColumnmaps().get("name_Fname"):""));
        placeofbirth.setText(pc.getDetails().get("place_of_birth")!=null?pc.getDetails().get("place_of_birth"):"");
        address.setText(pc.getDetails().get("present_address")!=null?pc.getDetails().get("present_address"):"");
        age.setText(""+age(pc)+ "d ");
        dateofbirth.setText(pc.getColumnmaps().get("child_dob")!=null?pc.getColumnmaps().get("child_dob"):"");
        if((pc.getDetails().get("child_nid")!=null?pc.getDetails().get("child_nid"):"").length()>0) {
            nid.setText("NID: " + (pc.getDetails().get("child_nid") != null ? pc.getDetails().get("child_nid") : ""));
            nid.setVisibility(View.VISIBLE);
        }else{
            nid.setVisibility(View.GONE);
        }
        if ((pc.getDetails().get("gender") != null ? pc.getDetails().get("gender") : "").equalsIgnoreCase("Female")) {
            profilepic.setImageResource(R.drawable.child_girl_infant);
//                newborn_or_fp.setText("Family Planning");
        } else {
            profilepic.setImageResource(R.drawable.child_boy_infant);
//                newborn_or_fp.setVisibility(View.INVISIBLE);
        }
        try {
            if (pc.getDetails().get("profilepic") != null) {
                ChildDetailActivity.setImagetoHolder((Activity) context, pc.getDetails().get("profilepic"), profilepic, R.drawable.child_boy_infant);
            }
        }catch (Exception e){

        }



        constructRiskFlagView(pc,itemView);

        itemView.setLayoutParams(clientViewLayoutParams);
    }



    private Long age(CommonPersonObjectClient ancclient) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date edd_date = format.parse(ancclient.getColumnmaps().get("child_dob")!=null?ancclient.getColumnmaps().get("child_dob") :"");
            Calendar thatDay = Calendar.getInstance();
            thatDay.setTime(edd_date);

            Calendar today = Calendar.getInstance();

            long diff = today.getTimeInMillis() - thatDay.getTimeInMillis();

            long days = diff / (24 * 60 * 60 * 1000);

            return days;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }

    }



    private void constructRiskFlagView(CommonPersonObjectClient pc,  View itemView) {
//        AllCommonsRepository allancRepository = org.ei.opensrp.Context.getInstance().allCommonsRepositoryobjects("mcaremother");
//        CommonPersonObject ancobject = allancRepository.findByCaseID(pc.entityId());
//        AllCommonsRepository allelcorep = org.ei.opensrp.Context.getInstance().allCommonsRepositoryobjects("elco");
//        CommonPersonObject elcoparent = allelcorep.findByCaseID(ancobject.getRelationalId());

        ImageView hrp = (ImageView)itemView.findViewById(R.id.hrp);
        ImageView hp = (ImageView)itemView.findViewById(R.id.hr);
        ImageView vg = (ImageView)itemView.findViewById(R.id.vg);

            vg.setVisibility(View.GONE);


            hrp.setVisibility(View.GONE);

            hp.setVisibility(View.GONE);


//        if(pc.getDetails().get("FWWOMAGE")!=null &&)

    }


    @Override
    public SmartRegisterClients updateClients(FilterOption villageFilter, ServiceModeOption serviceModeOption,
                                              FilterOption searchFilter, SortOption sortOption) {
        return null;
    }

    @Override
    public void onServiceModeSelected(ServiceModeOption serviceModeOption) {
        // do nothing.
    }

    @Override
    public OnClickFormLauncher newFormLauncher(String formName, String entityId, String metaData) {
        return null;
    }

    public LayoutInflater inflater() {
        return inflater;
    }

    @Override
    public View inflatelayoutForCursorAdapter() {
        return (ViewGroup) inflater().inflate(R.layout.smart_register_crvs_child_client, null);
    }

    class alertTextandStatus{
        String alertText ,alertstatus;

        public alertTextandStatus(String alertText, String alertstatus) {
            this.alertText = alertText;
            this.alertstatus = alertstatus;
        }

        public String getAlertText() {
            return alertText;
        }

        public void setAlertText(String alertText) {
            this.alertText = alertText;
        }

        public String getAlertstatus() {
            return alertstatus;
        }

        public void setAlertstatus(String alertstatus) {
            this.alertstatus = alertstatus;
        }
    }
}
