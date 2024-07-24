package com.example.testoviy_dating.thirdFragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testoviy_dating.R
import com.example.testoviy_dating.databinding.FragmentChatBinding
import com.example.testoviy_dating.models.ChatMessage
import com.example.testoviy_dating.newadapters.ChatAdapter
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.messaging.FirebaseMessaging

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ChatFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ChatFragment : Fragment() {

    private lateinit var binding: FragmentChatBinding
    private lateinit var firestore: FirebaseFirestore
    private lateinit var chatAdapter: ChatAdapter
    private lateinit var messageList: ArrayList<ChatMessage>
    private var senderId: String? = null
    private var receiverId: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChatBinding.inflate(inflater, container, false)
        firestore = FirebaseFirestore.getInstance()
        messageList = ArrayList()





        senderId = arguments?.getString("senderId")
        receiverId = arguments?.getString("receiverId")

        if (senderId != null && receiverId != null) {
            chatAdapter = ChatAdapter(messageList, senderId.orEmpty())
            binding.recyclerView.adapter = chatAdapter
            binding.recyclerView.layoutManager = LinearLayoutManager(context).apply {
                stackFromEnd = true
            }

            loadMessages()

            binding.sendButton.setOnClickListener {
                val message = binding.messageEditText.text.toString()
                if (message.isNotEmpty()) {
                    sendMessage(message)
                }
            }
        } else {
            Toast.makeText(context, "Chat participants not specified", Toast.LENGTH_SHORT).show()
        }

        return binding.root
    }

    private fun loadMessages() {
        val chatId = getChatId(senderId!!, receiverId!!)
        firestore.collection("chats").document(chatId).collection("messages")
            .orderBy("timestamp", Query.Direction.ASCENDING)
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    Log.w("ChatFragment", "Listen failed.", e)
                    return@addSnapshotListener
                }

                if (snapshot != null && !snapshot.isEmpty) {
                    messageList.clear()
                    for (doc in snapshot.documents) {
                        val chatMessage = doc.toObject(ChatMessage::class.java)
                        if (chatMessage != null) {
                            messageList.add(chatMessage)
                        }
                    }
                    chatAdapter.notifyDataSetChanged()
                    binding.recyclerView.scrollToPosition(messageList.size - 1)
                }
            }
    }

    private fun sendMessage(message: String) {
        val chatId = getChatId(senderId!!, receiverId!!)
        val chatMessage = ChatMessage(senderId!!, receiverId!!, message, System.currentTimeMillis())


        firestore.collection("chats").document(chatId).collection("messages")
            .add(chatMessage)
            .addOnSuccessListener {
                binding.messageEditText.text.clear()
            }
            .addOnFailureListener {
                Toast.makeText(context, "Failed to send message.", Toast.LENGTH_SHORT).show()
            }
    }

    private fun getChatId(senderId: String, receiverId: String): String {
        return if (senderId < receiverId) {
            "$senderId-$receiverId"
        } else {
            "$receiverId-$senderId"
        }
    }





    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ChatFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ChatFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}