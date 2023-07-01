import android.util.Log
import com.google.firebase.Timestamp
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

suspend fun sendMessage(
    currentUserEmail: String,
    recipientEmail: String,
    messageContent: String
) {
    val chatCollection = Firebase.firestore.collection("chats")
    val chatQuery = chatCollection
        .whereArrayContains("participants", currentUserEmail)
        .whereIn("participants", listOf(recipientEmail))
        .limit(1)
        .get()
        .await()

    val chatDocument = chatQuery.documents.firstOrNull()

    if (chatDocument != null) {
        Log.d("ACK", "Chat info is not null")
        val messageData = hashMapOf(
            "sender" to currentUserEmail,
            "content" to messageContent,
            "timestamp" to Timestamp.now()
        )
        chatDocument.reference.collection("messages")
            .add(messageData)
            .await()
    } else {
        // Create a new chat document
        Log.d("ACK", "Chat info is null")
        val participants = listOf(currentUserEmail, recipientEmail)
        val newChatDocument = chatCollection.document()
        newChatDocument.set(
            hashMapOf(
                "participants" to participants
            )
        ).await()

        // Add the message to the new chat document
        val messageData = hashMapOf(
            "sender" to currentUserEmail,
            "content" to messageContent,
            "timestamp" to Timestamp.now()
        )
        newChatDocument.collection("messages")
            .add(messageData)
            .await()
    }
}