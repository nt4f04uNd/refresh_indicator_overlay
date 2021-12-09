import 'package:flutter/material.dart';

void main() {
  runApp(const App());
}

class App extends StatelessWidget {
  const App({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'App',
      home: Scaffold(
        body: RefreshIndicator(
          triggerMode: RefreshIndicatorTriggerMode.anywhere,
          onRefresh: () => Future.delayed(const Duration(seconds: 1)),
          child: ListView.builder(
            itemBuilder: (context, index) => ListTile(
              title: Text('$index'),
            ),
            itemCount: 19,
          ),
        ),
      ),
    );
  }
}
