/**
 * Copyright (C) 2013-2014 EaseMob Technologies. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.easemob.chatuidemo.activity.group;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.baidu.mapapi.cloud.BoundSearchInfo;
import com.easemob.EMError;
import com.easemob.applib.controller.HXSDKHelper;
import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMContactManager;
import com.easemob.chat.EMGroup;
import com.easemob.chat.EMGroupManager;
import com.easemob.chatuidemo.DemoApplication;
import com.easemob.chatuidemo.DemoHXSDKModel;
import com.easemob.chatuidemo.activity.BaseActivity;
import com.easemob.chatuidemo.activity.ChatActivity;
import com.easemob.chatuidemo.activity.MyFriendActivity;
import com.wenpy.jcc.R;
import com.easemob.exceptions.EaseMobException;

/**
 * 学生群
 * 
 */
public class StudentGroupActivity extends BaseActivity {
    private TextView tvMyFriend;
    private TextView tvGroupName;
    protected List<EMGroup> grouplist;
    private String mGroupId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_group);
        
        tvMyFriend = (TextView)findViewById(R.id.tv_myfriend);
        tvGroupName = (TextView)findViewById(R.id.tv_groupname);

        try {
            List<String> usernames = EMContactManager.getInstance().getContactUserNames();
            tvMyFriend.setText("我的好友("+usernames.size()+")人");
        } catch (EaseMobException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        grouplist = EMGroupManager.getInstance().getAllGroups();
        Bundle args = new Bundle();
        if(grouplist.size() > 0){
            EMGroup group = grouplist.get(0);
            mGroupId = group.getId();
            String GroupName = group.getName();
            if(group.getAffiliationsCount()>0)
                GroupName += "("+group.getAffiliationsCount()+")人";
            tvGroupName.setText(GroupName);
        }
    }
    
    public void back(View view) {
        finish();
    }
    
    public void MyFriendClick(View v){
        startActivityForResult(new Intent(this, MyFriendActivity.class), 0);        
    }
    
    public void MyGroupClick(View v){
        if(mGroupId.equals(""))
            return;
        Intent intent = new Intent(StudentGroupActivity.this, ChatActivity.class);
        intent.putExtra("chatType", ChatActivity.CHATTYPE_GROUP);
        intent.putExtra("groupId", mGroupId);
        startActivityForResult(intent, 0);
    }
}
