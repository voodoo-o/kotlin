package com.example.alistwithdetails.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.alistwithdetails.data.model.Repo
import com.example.alistwithdetails.data.repository.MockRepoRepository

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RepoDetailsScreen(
    repoId: Long,
    navController: NavController,
    viewModel: RepoDetailsViewModel = viewModel(
        factory = RepoDetailsViewModelFactory(repoId, MockRepoRepository())
    )
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = uiState.repo?.name ?: "Details") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            when {
                uiState.isLoading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                uiState.error != null -> {
                    Text(
                        text = uiState.error!!,
                        modifier = Modifier.align(Alignment.Center),
                        color = MaterialTheme.colorScheme.error
                    )
                }
                uiState.repo != null -> {
                    RepoDetailsContent(repo = uiState.repo!!)
                }
            }
        }
    }
}

@Composable
fun RepoDetailsContent(repo: Repo) {
    Column(modifier = Modifier.fillMaxSize()) {
        Text(text = repo.name, style = MaterialTheme.typography.headlineLarge)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "by ${repo.owner}", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = repo.description ?: "No description available.", style = MaterialTheme.typography.bodyLarge)
        Spacer(modifier = Modifier.height(16.dp))
        
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(imageVector = Icons.Default.Star, contentDescription = "Stars")
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = "${repo.stars} stars")
            }
            Text(text = "||")
            Text(text = "${repo.forks} forks")
        }
        Spacer(modifier = Modifier.height(8.dp))
        repo.language?.let {
            Text(text = "Language: $it", style = MaterialTheme.typography.bodyMedium)
        }
    }
}
