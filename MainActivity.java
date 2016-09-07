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

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.antonioleiva.mvpexample.app.R;
import com.antonioleiva.mvpexample.app.adapters.UserAdapter;
import com.antonioleiva.mvpexample.app.model.User;
import com.antonioleiva.mvpexample.app.util.DividerItemDecoration;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.recyclerview.adapters.SlideInLeftAnimationAdapter;
import jp.wasabeef.recyclerview.animators.ScaleInRightAnimator;

/**
 * Juan Martin Bernal
 */
public class MainActivity extends Activity implements MainView {

    @BindView(R.id.list)
    RecyclerView recyclerView;
    @BindView(R.id.progress) ProgressBar progressBar;
    private MainPresenter presenter;
    UserAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        recyclerView.setItemAnimator(new ScaleInRightAnimator());
        recyclerView.getItemAnimator().setRemoveDuration(400);
        presenter = new MainPresenterImpl(this, new FindItemsInteractorImpl());
    }

    @Override protected void onResume() {
        super.onResume();
        presenter.onResume();
    }

   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }*/

    @Override protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

    @Override public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.INVISIBLE);
    }

    @Override public void hideProgress() {
        progressBar.setVisibility(View.INVISIBLE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    @Override public void setItems(final List<User> items) {

        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.addItemDecoration(new DividerItemDecoration(this,LinearLayoutManager.VERTICAL));
        recyclerView.setLayoutManager(layoutManager);

        adapter  = new UserAdapter(items, new UserAdapter.OnItemClickListener() {
            @Override public void onItemClick(User item,int position) {
                presenter.onItemClicked(adapter,position );
            }

            @Override
            public void onLongClick(int position) {
               presenter.onItemLongClicked(adapter,position);
            }
        });
        SlideInLeftAnimationAdapter slideInLeftAnimationAdapter = new SlideInLeftAnimationAdapter(adapter);
        slideInLeftAnimationAdapter.setDuration(600);
        recyclerView.setAdapter(slideInLeftAnimationAdapter/* new SlideInLeftAnimationAdapter(alphaAdapter)*/);

    }

    @Override public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
