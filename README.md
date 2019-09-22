SampleAppWithTypicode
=====================

**Developed by:[Prashanth Ramakrishnan](prashanth_r03@yahoo.co.in)**

**Features**
- Language used is Kotlin.
- Fetches the data from the provided [API](https://jsonplaceholder.typicode.com/posts) and shows the list in a recycler view.
- Swipe to refresh fetches data from the network again.
- List contains title and body fetched from the API.
- Clicking on the list shows the post ID.
- Long clicking on the list shows a dialog with which the post can be deleted locally.
- There is no storage in the application, everything is live via the network.
- Common errors including network are handled as a Snackbar message.
- Includes unit tests for presenters and instrumentation tests for the application flow. For instrumentation
tests I chose to use Robotium.
- Add your own release keys to install the app in release flavor.

Refer [here](https://gist.github.com/jemshit/767ab25a9670eb0083bafa65f8d786bb) for proguard rules.

**Note**
- There is no DB in this application, data is shown as is from the API calls!
- Tested on Motorola Moto G4 (not on emulator), Android version 7.0

**Open source libraries used**

- **[Dagger2](https://github.com/google/dagger)**
- **[RxJava2](https://github.com/ReactiveX/RxJava)**
- **[RxAndroid](https://github.com/ReactiveX/RxAndroid)**
- **[Retrofit2](https://github.com/square/retrofit)**
- **[OkHttp3](https://github.com/square/okhttp)**
- **[Gson](https://github.com/google/gson)**
- **[Timber](https://github.com/JakeWharton/timber)**
- **[Robotium](https://github.com/RobotiumTech/robotium)**
- **[MockWebServer](https://github.com/square/okhttp/tree/master/mockwebserver)**
- **[Mockito-kotlin](https://github.com/nhaarman/mockito-kotlin)**
- **[Mockito](https://github.com/mockito/mockito)**

### License

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.