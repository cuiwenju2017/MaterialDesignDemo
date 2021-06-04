package com.example.materialdesigndemo;

import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.example.materialdesigndemo.databinding.FragmentTestBinding;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.shape.CornerTreatment;
import com.google.android.material.shape.CutCornerTreatment;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.RoundedCornerTreatment;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.google.android.material.shape.ShapePath;
import com.google.android.material.shape.TriangleEdgeTreatment;

public class TestFragment extends Fragment {

    private FragmentTestBinding binding;
    private int type;

    public TestFragment(Integer type) {
        Bundle b = new Bundle();
        b.putInt("type", type);
        setArguments(b);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentTestBinding.inflate(inflater, container, false);
        type = getArguments().getInt("type");
        initDate(type);
        return binding.getRoot();
    }

    private void initDate(int type) {
        if (type == 0) {
            binding.ll0.setVisibility(View.VISIBLE);
        } else if (type == 1) {
            binding.ll1.setVisibility(View.VISIBLE);
        }

        binding.etName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (binding.etName.getText().length() > binding.tilName.getCounterMaxLength()) {
                    binding.tilName.setError("输入内容超过上限");
                } else {
                    binding.tilName.setError(null);
                }
            }
        });

        // 代码设置圆角、切角
        ShapeAppearanceModel shapeAppearanceModel1 = ShapeAppearanceModel.builder()
                .setTopLeftCorner((new RoundedCornerTreatment()))
                .setTopLeftCornerSize(80.0F)
                .setBottomRightCorner((new RoundedCornerTreatment()))
                .setBottomRightCornerSize(80.0F)
                .setTopRightCorner((new CutCornerTreatment()))
                .setTopRightCornerSize(80.0F)
                .setBottomLeftCorner((new CutCornerTreatment()))
                .setBottomLeftCornerSize(80.0F)
                .build();
        binding.siv1.setShapeAppearanceModel(shapeAppearanceModel1);

        // 代码设置 角和边
        ShapeAppearanceModel shapeAppearanceModel2 = ShapeAppearanceModel.builder().
                setAllCorners(new RoundedCornerTreatment())
                .setAllCornerSizes(50f)
                .setAllEdges(new TriangleEdgeTreatment(50f, false))
                .build();
        MaterialShapeDrawable drawable2 = new MaterialShapeDrawable(shapeAppearanceModel2);
        drawable2.setTint(ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark));
        drawable2.setPaintStyle(Paint.Style.FILL_AND_STROKE);
        drawable2.setStrokeWidth(50f);
        drawable2.setStrokeColor(ContextCompat.getColorStateList(getActivity(), R.color.colorAccent));
        binding.text2.setTextColor(Color.WHITE);
        binding.text2.setBackground(drawable2);

        // 代码设置 聊天框效果
        ShapeAppearanceModel shapeAppearanceModel3 = ShapeAppearanceModel.builder()
                .setAllCorners(new RoundedCornerTreatment())
                .setAllCornerSizes(20f)
                .setRightEdge(new TriangleEdgeTreatment(20f, false))
                .build();
        MaterialShapeDrawable drawable3 = new MaterialShapeDrawable(shapeAppearanceModel3);
        drawable3.setTint(ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark));
        drawable3.setPaintStyle(Paint.Style.FILL);
        ((ViewGroup) binding.text3.getParent()).setClipChildren(false);
        binding.text3.setTextColor(Color.WHITE);
        binding.text3.setBackground(drawable3);
    }
}