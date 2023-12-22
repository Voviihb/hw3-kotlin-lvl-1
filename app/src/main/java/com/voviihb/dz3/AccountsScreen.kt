package com.voviihb.dz3

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment

class AccountsScreenFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .paint(
                            painterResource(id = R.drawable.background),
                            contentScale = ContentScale.FillBounds
                        )
                ) {
                    Column {
                        Text("You will find accounts here later", fontSize = 24.sp)
                        Button(onClick = {
                            parentFragmentManager.beginTransaction()
                                .replace(R.id.fragment_container, LoginScreenFragment.newInstance())
                                .commit()
                        }) {
                            Text("Navigate to login fragment")
                        }
                    }

                }
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            AccountsScreenFragment()
    }
}