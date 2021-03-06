package com.example.fitlife;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

//Class used to hyperlink to other websites that give users the recipes for simple dishes.
public class MealPlanIdeas extends AppCompatActivity {
    TextView oats, bagel, smoothie, chicken, salmon, pasta, steak, pizza, shake;
    Button meal, home;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_plan_ideas);
        home = findViewById(R.id.homeButton);
        oats = findViewById(R.id.oats);
        bagel = findViewById(R.id.bagel);
        smoothie = findViewById(R.id.smoothie);
        chicken = findViewById(R.id.chicken);
        salmon = findViewById(R.id.salmon);
        pasta = findViewById(R.id.pasta);
        steak = findViewById(R.id.steak);
        pizza = findViewById(R.id.pizza);
        shake = findViewById(R.id.shake);

//Create link activity for breakfast
        oats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://downshiftology.com/recipes/overnight-oats/")));
                finish();
            }
        });
        //Create hyperlink for bagel recipe
        bagel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://themom100.com/recipe/breakfast-bagel-sandwich/")));
                finish();
            }
        });
        //Create hyperlink for smoothie recipes
        smoothie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://kristineskitchenblog.com/21-healthy-breakfast-smoothie-recipes/")));
                finish();
            }
        });
        //Create link activity for lunch ideas
        chicken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.campbells.com/recipes/one-dish-chicken-rice-bake/"
)));
                finish();
            }
        });
        //Create hyperlink for salmon recipe
        salmon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.skinnytaste.com/salmon-fried-rice/"
)));
                finish();
            }
        });
        //Create hyperlink for speciality pasta
        pasta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.foodandwine.com/pasta-noodles/pasta"
)));
                finish();
            }
        });
        //Create link activity for dinner meals
        steak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.eatwell101.com/garlic-butter-steak-and-potatoes-recipe"
)));
                finish();
            }
        });
        //Create hyperlink for protein pizza recipe
        pizza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://fithappyfoodie.com/2019/05/high-protein-low-calorie-pizza-400-cal-meal.html"
)));
                finish();
            }
        });
        //Create hyperlink for end of day shake
        shake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.allrecipes.com/recipe/245163/the-best-post-workout-shake/"
)));
                finish();
            }
        });
        //Create button to go back to meal tracker
        meal = findViewById(R.id.mealBtn);
        meal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MealWaterTracking.class));
                finish();
            }
    });
        //Create button to go back to home
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();
            }
        });
}
}