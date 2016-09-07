/*
 *
 *  * Copyright (C) 2014 Antonio Leiva Gordillo.
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *      http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */

package com.antonioleiva.mvpexample.app.main;

import com.antonioleiva.mvpexample.app.R;
import com.antonioleiva.mvpexample.app.adapters.UserAdapter;
import com.antonioleiva.mvpexample.app.model.User;

import java.util.List;

public class MainPresenterImpl implements MainPresenter, FindItemsInteractor.OnFinishedListener {

    private MainView mainView;
    private FindItemsInteractor findItemsInteractor;

    public MainPresenterImpl(MainView mainView, FindItemsInteractor findItemsInteractor) {
        this.mainView = mainView;
        this.findItemsInteractor = findItemsInteractor;
    }

    @Override public void onResume() {
        if (mainView != null) {
            mainView.showProgress();
        }

        findItemsInteractor.findItems(this);
    }

    @Override public void onItemClicked(UserAdapter userAdapter, int position) {
        if (mainView != null) {
            mainView.showMessage("tap on "+position );
            User user = new User();
            user.name = "juan" ;
            user.image = R.drawable.ic_action_accounts;

            userAdapter.planetList.add(position + 1, user);
            userAdapter.notifyItemInserted(position + 1);
        }
    }

    @Override
    public void onItemLongClicked(UserAdapter userAdapter, int position) {
        userAdapter.planetList.remove(position);
        userAdapter.notifyItemRemoved(position);
    }

    @Override public void onDestroy() {
        mainView = null;
    }

    @Override public void onFinished(List<User> items) {
        if (mainView != null) {
            mainView.setItems(items);
            mainView.hideProgress();
        }
    }

    public MainView getMainView() {
        return mainView;
    }
}
