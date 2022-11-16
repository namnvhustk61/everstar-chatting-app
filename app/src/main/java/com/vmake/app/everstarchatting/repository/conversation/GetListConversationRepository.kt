package com.vmake.app.everstarchatting.repository.conversation

import com.vmake.app.base.helper.runInBackground
import com.vmake.app.everstarchatting.repository.conversation.model.Conversation

class GetListConversationRepository {
    companion object {
        fun newInstance() = GetListConversationRepository()
    }

    suspend fun getListFriend() = runInBackground {

        listOf<Conversation>(
            Conversation(
                "https://cdn.imgbin.com/16/1/11/imgbin-cartoon-funny-animal-cartoon-lion-material-V6giQF0z2jAuBWf8NgBJHFTkB.jpg",
                "John",
                "At the moment it seems to be "
            ),
            Conversation(
                "https://e7.pngegg.com/pngimages/230/8/png-clipart-birds-birds-wild-animals.png",
                "Alien",
                " scroll bars"
            ),
            Conversation(
                "https://w7.pngwing.com/pngs/667/420/png-transparent-hummingbird-watercolor-painting-visual-arts-birds-blue-animals-fauna-thumbnail.png",
                "Dog",
                " it without inflating an XML layout"
            ),
            Conversation(
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT_IhzSptgGR9RtLmh72hqvZw7igitwYuML6fC8iFpi78MX51pG0jZaQcpjcLfC3XZ-Ulc&usqp=CAU",
                "Doping",
                "initialize your RecyclerView use",
            ),
            Conversation(
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTEE92TFzYeddzPzGrSEbCFxNAuh_ET4Sbbsq_dpm4B9DiVtnpvgZFHh-X3XZj-0VKH8Sc&usqp=CAU",
                "Guide",
                " attribute to the style"
            ),
            Conversation(
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR5RbpeunX9cPIU9yHQtntL1o4xw_MT7Z4otALlNxbyPDR_6p2sHL4rTBEBJbsVOA0XXGQ&usqp=CAU",
                "Sami",
                "I would prefer to use ContextThemeWrapper for that, because it can be used dynamically from code."
            ),
            Conversation(
                "https://images.vexels.com/media/users/3/151976/isolated/lists/a7249fdf37d232a20807a2efb5cc2e7b-squirrel-animal-cartoon.png",
                "Sen",
                "@IgorGanapolsky No need to set it, unless you "
            ),
            Conversation(
                "https://pngimg.com/uploads/deer/deer_PNG10185.png", "Ronaldo",
                "The best of year"
            ),
            Conversation(
                "https://e7.pngegg.com/pngimages/74/284/png-clipart-sheep-goat-drawing-sheep-mammal-animals.png",
                "Chipper",
                "Now you can inflate and add the RecyclerView with scrollbars everywhere you want:"
            ),
            Conversation("", "Hart", "Hello guys"),
            Conversation(
                "https://i.pinimg.com/236x/93/0a/b0/930ab0bf0a097dfab07fe8944d4ece61.jpg",
                "Shaw",
                "Play with me"
            )
        )
    }
}