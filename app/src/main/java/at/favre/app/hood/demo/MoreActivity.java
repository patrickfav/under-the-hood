/*
 *  Copyright 2016 Patrick Favre-Bulle
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package at.favre.app.hood.demo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import at.favre.app.hood.demo.databinding.ActivityMoreBinding;
import at.favre.lib.hood.Hood;
import at.favre.lib.hood.interfaces.actions.ManagerControl;

public class MoreActivity extends AppCompatActivity {
    private ManagerControl shakeControl;

    public static void start(Context context) {
        Intent starter = new Intent(context, MoreActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActivityMoreBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_more);

        shakeControl = Hood.ext().registerShakeToOpenDebugActivity(this, DebugDarkMultiPageActivity.createIntent(this, DebugDarkMultiPageActivity.class));

        binding.btnStopShake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.tvTryShake.setVisibility(View.INVISIBLE);
                v.setEnabled(false);
                shakeControl.stop();
            }
        });

        binding.tvDoubletap.setOnTouchListener(Hood.ext().createArbitraryTapListener(2, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DebugDarkActivity.start(MoreActivity.this, DebugDarkActivity.class);
            }
        }));

        binding.tvTripletap.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(MoreActivity.this, "LONG CLICK", Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        binding.tvTripletap.setOnTouchListener(Hood.ext().createArbitraryTapListener(3, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DebugLightActivity.start(MoreActivity.this, DebugLightActivity.class);
            }
        }));
    }

    @Override
    protected void onResume() {
        super.onResume();
        shakeControl.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        shakeControl.stop();
    }
}
