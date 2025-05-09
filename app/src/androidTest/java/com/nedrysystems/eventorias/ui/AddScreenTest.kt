package com.nedrysystems.eventorias.ui

import android.content.Context
import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performTextInput
import androidx.navigation.compose.rememberNavController
import androidx.test.core.app.ApplicationProvider
import com.nedrysystems.eventorias.R
import com.nedrysystems.eventorias.domain.mapper.EventMapper
import com.nedrysystems.eventorias.domain.useCase.event.container.EventUseCases
import com.nedrysystems.eventorias.domain.useCase.user.container.UserUseCases
import com.nedrysystems.eventorias.ui.addScreen.AddScreen
import com.nedrysystems.eventorias.ui.addScreen.AddViewModel
import io.mockk.mockk
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class AddScreenTest {
    private lateinit var addViewModel: AddViewModel
    private lateinit var mockEventUseCases: EventUseCases
    private lateinit var mockEventMapper: EventMapper
    private lateinit var mockUserUseCases: UserUseCases

    @Before
    fun setUp() {

        mockEventUseCases = mockk()
        mockEventMapper = mockk()
        mockUserUseCases = mockk()


        addViewModel = AddViewModel(mockEventUseCases, mockEventMapper, mockUserUseCases)
    }

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun test_fill_fields_and_validate_button_are_disable() {

        val context = ApplicationProvider.getApplicationContext<Context>()
        val titleLabel = context.getString(R.string.title_field)
        val descriptionLabel = context.getString(R.string.description_field)
        val dateLabel = context.getString(R.string.date_field)
        val hourLabel = context.getString(R.string.hour_field)
        val addressLabel = context.getString(R.string.address_field)
        val validateLabel = context.getString(R.string.validate_button)



        composeTestRule.setContent {
            AddScreen(
                navController = rememberNavController(),
                viewModel = addViewModel
            )
        }


        composeTestRule.onNodeWithContentDescription(titleLabel)
            .performTextInput("Titre Test")
        composeTestRule.onNodeWithContentDescription(descriptionLabel)
            .performTextInput("Description Test")
        composeTestRule.onNodeWithContentDescription(dateLabel)
            .performTextInput("01/01/2025")
        composeTestRule.onNodeWithContentDescription(hourLabel)
            .performTextInput("12:00")
        composeTestRule.onNodeWithContentDescription(addressLabel)
            .performTextInput("123 Rue Test")

        composeTestRule.onNodeWithContentDescription(validateLabel)
            .assertIsNotEnabled()

    }
}