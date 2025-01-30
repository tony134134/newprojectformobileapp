package com.example.mobileapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class InfoFragment extends Fragment {

    RecyclerView recyclerView;
    TextView welcomeText;
    EditText messageEdit;
    ImageButton sendButton;
    List<Message> messageList;
    MessageAdepter messageAdepter;

    public static final MediaType JSON = MediaType.get("application/json");

    OkHttpClient client = new OkHttpClient();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_info, container, false);

        messageList = new ArrayList<>();

        recyclerView = view.findViewById(R.id.recyclerView);
        welcomeText = view.findViewById(R.id.welcomeText);
        messageEdit = view.findViewById(R.id.messageEdit);
        sendButton = view.findViewById(R.id.sendButton);

        messageAdepter = new MessageAdepter(messageList);
        recyclerView.setAdapter(messageAdepter);
        LinearLayoutManager llm = new LinearLayoutManager(requireContext());
        llm.setStackFromEnd(true);
        recyclerView.setLayoutManager(llm);

        sendButton.setOnClickListener((v)->{
            String question = messageEdit.getText().toString().trim();
            addToChat(question, Message.SENT_BY_ME);
            messageEdit.setText("");
            callAPI(question);
            welcomeText.setVisibility(View.GONE);
        });

        return view;
    }

    void addToChat(String message, String sendBy) {
        requireActivity().runOnUiThread(() -> {
            messageList.add(new Message(message, sendBy));
            messageAdepter.notifyDataSetChanged();
            recyclerView.smoothScrollToPosition(messageAdepter.getItemCount());
        });
    }

    void addResponse(String response){

        messageList.remove(messageList.size()-1);
        addToChat(response, Message.SEND_BY_BOT);

    }

    void callAPI(String question) {
        messageList.add(new Message("Typing... ", Message.SEND_BY_BOT));
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("model", "gpt-4o-mini");
            jsonBody.put("messages", new JSONArray().put(new JSONObject()
                    .put("role", "user")
                    .put("content", question)));
            jsonBody.put("max_tokens", 3000);
            jsonBody.put("temperature", 0);
        } catch (JSONException e) {
            addResponse("Error creating JSON body: " + e.getMessage());
            return;
        }

        RequestBody body = RequestBody.create(jsonBody.toString(), JSON);
        Request request = new Request.Builder()
                .url("https://api.openai.com/v1/chat/completions")
                .header("Authorization", "Bearer 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")
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                addResponse("Fail to load response due to: " + e.getMessage());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                try {
                    if (response.isSuccessful() && response.body() != null) {
                        String responseBody = response.body().string();
                        JSONObject jsonObject = new JSONObject(responseBody);
                        JSONArray choices = jsonObject.getJSONArray("choices");
                        String result = choices.getJSONObject(0).getJSONObject("message").getString("content");
                        addResponse(result.trim());
                    } else {
                        addResponse("Fail to load response: " + response.code() + " " + response.message());
                    }
                } catch (Exception e) {
                    addResponse("Error parsing response: " + e.getMessage());
                } finally {
                    response.close();
                }
            }
        });
    }

}
