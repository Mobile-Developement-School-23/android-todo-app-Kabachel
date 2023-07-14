package com.example.todoapp.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.todoapp.R
import com.example.todoapp.domain.entity.PriorityType
import com.example.todoapp.domain.entity.TodoItem
import com.example.todoapp.presentation.resources.ExtendedTheme
import com.example.todoapp.presentation.resources.TodoAppColor
import com.example.todoapp.presentation.todoitems.Event
import com.example.todoapp.presentation.todoitems.State
import com.example.todoapp.presentation.utils.EmptySpacer
import java.time.LocalDateTime

/**
 * @author Данила Шабанов on 13.07.2023.
 */
@Composable
internal fun TodoAppUi(
    state: State,
    onEvent: (event: Event) -> Unit,
) {
    when (state) {
        State.EmptyContent -> {}

        is State.Content -> {
            ShowContent(state, onEvent)
        }
    }
}

@Composable
private fun ShowContent(
    state: State.Content,
    onEvent: (event: Event) -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(ExtendedTheme.colors.backPrimary)
            .systemBarsPadding()
            .padding(horizontal = 8.dp),
    ) {
        Column {
            TopBar(state.screenItems.size)
            EmptySpacer(16.dp)
            TodoItemsList(state, onEvent)
        }

        IconButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 24.dp, end = 8.dp)
                .clip(CircleShape)
                .background(TodoAppColor.Blue),
            onClick = {
                onEvent(Event.AddTodoItem)
            }
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_plus),
                contentDescription = null,
            )
        }
    }
}

@Composable
private fun TopBar(
    itemsCount: Int,
) {
    Column(modifier = Modifier.padding(top = 50.dp, start = 52.dp)) {
        Text(
            text = stringResource(id = R.string.todoItemsTitle),
            style = ExtendedTheme.typography.title,
        )
        Row(modifier = Modifier.padding(end = 16.dp)) {
            Text(
                modifier = Modifier
                    .alpha(0.3f)
                    .weight(1f),
                text = stringResource(id = R.string.todoItemsSubtitle) + itemsCount,
                style = ExtendedTheme.typography.subtitle,
            )
            Image(
                modifier = Modifier.size(24.dp),
                painter = painterResource(id = R.drawable.ic_eye),
                contentDescription = null,
                colorFilter = ColorFilter.tint(TodoAppColor.Blue)
            )
        }
    }
}

@Composable
private fun TodoItemsList(
    state: State.Content,
    onEvent: (event: Event) -> Unit,
) {
    LazyColumn {
        items(state.screenItems) { item ->
            TodoItem(item, onEvent)
        }
    }
}

@Composable
private fun TodoItem(
    todoItem: TodoItem,
    onEvent: (event: Event) -> Unit,
) {
    Row(
        modifier = Modifier
            .padding(vertical = 12.dp, horizontal = 16.dp)
            .background(ExtendedTheme.colors.backSecondary),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Checkbox(
            checked = todoItem.isDone,
            onCheckedChange = { onEvent(Event.CompleteTask(todoItem)) },
        )
        Text(
            modifier = Modifier.weight(1f),
            text = todoItem.text,
            style = ExtendedTheme.typography.bodyTitle,
        )
        Image(
            modifier = Modifier.size(24.dp),
            painter = painterResource(id = R.drawable.ic_info),
            contentDescription = null,
        )
    }
}

@Composable
@Preview
private fun ShowContentPreview() {
    ShowContent(
        State.Content(
            listOf(
                TodoItem(
                    id = "123",
                    text = "Text",
                    priority = PriorityType.LOW,
                    deadlineDate = null,
                    isDone = true,
                    createdDate = LocalDateTime.now(),
                    modifiedDate = LocalDateTime.now(),
                )
            ),
        )
    ) {}
}

@Composable
@Preview
private fun TopBarPreview() {
    TopBar(itemsCount = 1)
}

@Composable
@Preview
private fun TodoItemPreview() {
    TodoItem(
        todoItem = TodoItem(
            id = "123",
            text = "Text",
            priority = PriorityType.LOW,
            deadlineDate = null,
            isDone = true,
            createdDate = LocalDateTime.now(),
            modifiedDate = LocalDateTime.now(),
        ),
        onEvent = {}
    )
}