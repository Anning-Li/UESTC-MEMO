package com.starstudio.projectdemo.journal.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.starstudio.projectdemo.databinding.Fragment2JourBinding;
import com.starstudio.projectdemo.journal.activity.JournalItemDetailActivity;
import com.starstudio.projectdemo.journal.adapter.JourAdapter;
import com.starstudio.projectdemo.journal.api.JournalDaoService;
import com.starstudio.projectdemo.journal.data.JournalEntity;

import org.jetbrains.annotations.NotNull;
import org.reactivestreams.Subscription;

import java.util.Collections;
import java.util.List;

import io.reactivex.FlowableSubscriber;

/**
 * created by sgh 2021-7-29
 * “心情日记”页面下的"日记"板块
 */
public class JourFragment extends Fragment implements JourAdapter.OnJourItemClickListener {

    private Fragment2JourBinding binding;
    private JournalDaoService service;
    private JourAdapter adapter = new JourAdapter();

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        service = JournalDaoService.getInstance();
        service.loadAll().subscribe(new FlowableSubscriber<List<JournalEntity>>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        s.request(Integer.MAX_VALUE);
                    }

                    @Override
                    public void onNext(List<JournalEntity> journalEntities) {
                        Collections.reverse(journalEntities);
                        adapter.reset(journalEntities);
                    }

                    @Override
                    public void onError(Throwable t) {
                    }

                    @Override
                    public void onComplete() {
                    }
                });

        adapter.setListener(this::onJourItemClick);
        binding = Fragment2JourBinding.inflate(inflater, container, false);
        binding.recycler.setAdapter(adapter);
        binding.recycler.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        binding.recycler.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        return binding.getRoot();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    @Override
    public void onJourItemClick(View view, JournalEntity data) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("jourData", data);
        Intent intent = new Intent(getActivity(), JournalItemDetailActivity.class);
        intent.putExtra("data", bundle);
        startActivity(intent);
    }
}
