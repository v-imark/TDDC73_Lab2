import android.graphics.drawable.AnimatedImageDrawable
import androidx.compose.animation.Animatable
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.with
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.example.tddc73_lab2.R

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AnimatedLogo(
    imageId: Int,
) {
    var logoSize = 50.dp
    if(imageId == R.drawable.discover) logoSize = 30.dp

    AnimatedContent(
        targetState = imageId,
        label = "Anim",
        transitionSpec = {
            slideInVertically { -it } with slideOutVertically { it }
        }
    ) { img ->
        Image(
            painter = painterResource(id = img),
            contentDescription = "Visa",
            modifier = Modifier.height(logoSize)
        )
    }
    }

