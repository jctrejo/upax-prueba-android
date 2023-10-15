package com.android.upax_prueba_android.ui.detail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.android.upax_prueba_android.R;
import com.bumptech.glide.Glide;

public class PokemonDetailFragment extends Fragment {

    TextView nameTextView;

    TextView heightTextView;

    TextView widthTextView;

    ImageView postImageView;

    ImageView backImageView;

    ImageView favoriteImageView;

    boolean isFavorite;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        if (getArguments() != null) {
            String name = PokemonDetailFragmentArgs.fromBundle(getArguments()).getName();
            int height = PokemonDetailFragmentArgs.fromBundle(getArguments()).getHeight();
            int weight = PokemonDetailFragmentArgs.fromBundle(getArguments()).getWeight();
            String url = PokemonDetailFragmentArgs.fromBundle(getArguments()).getUrl();
            boolean favorite = PokemonDetailFragmentArgs.fromBundle(getArguments()).isFavorite();

            nameTextView = (TextView) view.findViewById(R.id.name_text_view);
            heightTextView = (TextView) view.findViewById(R.id.height_text_view);
            widthTextView = (TextView) view.findViewById(R.id.width_text_view);
            postImageView = (ImageView) view.findViewById(R.id.post_image_view);
            backImageView = (ImageView) view.findViewById(R.id.back_image_view);
            favoriteImageView = (ImageView) view.findViewById(R.id.favorite_image_view);

            view.findViewById(R.id.back_image_view).setOnClickListener(v -> Navigation.findNavController(v).navigateUp());
            view.findViewById(R.id.favorite_image_view).setOnClickListener(v -> {
                if (isFavorite) {
                    favoriteImageView.setImageResource(R.drawable.ic_heart);
                    isFavorite = false;
                } else {
                    favoriteImageView.setImageResource(R.drawable.ic_heart_on);
                    isFavorite = true;
                }
            });

            validateFavorite(favorite);
            initView(name, height, weight, url);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pokemon_detail, container, false);
    }

    private void initView(String name, int height, int weight, String url) {
        nameTextView.setText(name);
        heightTextView.setText(String.valueOf(height));
        widthTextView.setText(String.valueOf(weight));
        Glide.with(this).load(url).into(postImageView);
    }

    private void validateFavorite(boolean favorite) {
        if (favorite) {
            favoriteImageView.setImageResource(R.drawable.ic_heart_on);
        } else {
            favoriteImageView.setImageResource(R.drawable.ic_heart);
        }
    }
}
